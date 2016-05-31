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
import com.zhao.giftsaydemo.login.LoginActivity;
import com.zhao.giftsaydemo.util.MyListView;

import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 * 用户页面
 */
@BindContent(R.layout.fragment_user)
public class UserFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.fragment_user_lv)
    private MyListView listView;
    private LikeStrategyAdapter adapter;
    private StrategyDaoTool strategyDaoTool;
    private LikeChangedReceiver likeChangedReceiver;
    // 下方ListView无数据时的预显示内容
    @BindView(R.id.hint_tv)
    private TextView textView;
    @BindView(R.id.fragment_user_user_name_tv)
    private TextView nameTv;

    @Override
    public void initData() {
        // 注册广播
        registerReceiver();

        strategyDaoTool = new StrategyDaoTool();
        // 查询出已收藏的攻略
        final List<Strategy> strategyList = strategyDaoTool.queryStrategyByIsLiked(true);

        adapter = new LikeStrategyAdapter(context);
        adapter.setStrategies(strategyList);
        listView.setAdapter(adapter);
        listView.setEmptyView(textView);

        // 点击收藏的攻略, 跳转到攻略详情页面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, StrategyDetailsActivity.class);
                intent.putExtra("url", strategyList.get(position).getUrl());
                context.startActivity(intent);

            }
        });


        nameTv.setOnClickListener(this);

    }


    private void registerReceiver() {
        likeChangedReceiver = new LikeChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zhao.giftsaydemo.LikeChanged");
        context.registerReceiver(likeChangedReceiver, filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_user_user_name_tv:
                startActivity(new Intent(context, LoginActivity.class));
                break;

        }
    }


    // 接收礼物详情页发出的广播
    class LikeChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 重新获取一次已收藏的攻略
            List<Strategy> strategyList = strategyDaoTool.queryStrategyByIsLiked(true);
            adapter.setStrategies(strategyList);
        }
    }

    // 取消广播注册
    @Override
    public void onDestroy() {
        context.unregisterReceiver(likeChangedReceiver);
        super.onDestroy();
    }
}
