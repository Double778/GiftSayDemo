package com.zhao.giftsaydemo.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyDetailsActivity;
import com.zhao.giftsaydemo.db.GreenDaoSingle;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDao;
import com.zhao.giftsaydemo.db.StrategyDaoTool;
import com.zhao.giftsaydemo.util.MyListView;

import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 */
@BindContent(R.layout.fragment_user)
public class UserFragment extends BaseFragment {
    @BindView(R.id.fragment_user_lv)
    private MyListView listView;
    private LikeStrategyAdapter adapter;
    private StrategyDaoTool strategyDaoTool;
    private LikeChangedReceiver likeChangedReceiver;
    @BindView(R.id.hint_tv)
    private TextView textView;
    @Override
    public void initData() {
        registerReceiver();

        strategyDaoTool = new StrategyDaoTool();
        final List<Strategy> strategyList = strategyDaoTool.queryStrategyByIsLiked(true);

        adapter = new LikeStrategyAdapter(context);
        adapter.setStrategies(strategyList);
        listView.setAdapter(adapter);
        listView.setEmptyView(textView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, StrategyDetailsActivity.class);
                intent.putExtra("url", strategyList.get(position).getUrl());
                context.startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                strategyList.remove(position);
                adapter.setStrategies(strategyList);
                return true;
            }
        });
    }

    private void registerReceiver() {
        likeChangedReceiver = new LikeChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zhao.giftsaydemo.LikeChanged");
        context.registerReceiver(likeChangedReceiver, filter);
    }


    class LikeChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<Strategy> strategyList = strategyDaoTool.queryStrategyByIsLiked(true);
            adapter.setStrategies(strategyList);
        }
    }

    @Override
    public void onDestroy() {
        context.unregisterReceiver(likeChangedReceiver);
        super.onDestroy();
    }
}
