package com.zhao.giftsaydemo.user.like;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyDetailsActivity;
import com.zhao.giftsaydemo.db.GreenDaoTool;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.util.MyListView;
import com.zhao.giftsaydemo.value.GiftSayValues;

import java.util.List;

/**
 * Created by 华哥哥 on 16/6/3.
 */
@BindContent(R.layout.fragment_like_strategy)
public class LikeStrategyFragment extends BaseFragment {


    @BindView(R.id.fragment_user_lv)
    private MyListView listView;
    private LikeAdapter adapter;
    private GreenDaoTool greenDaoTool;
    private LikeChangedReceiver likeChangedReceiver;
    // 下方ListView无数据时的预显示内容
    @BindView(R.id.hint_tv)
    private TextView textView;

    @Override
    public void initData() {
        // 注册广播
        registerReceiver();

        greenDaoTool = new GreenDaoTool();
        // 查询出已收藏的攻略
        final List<Strategy> strategyList = greenDaoTool.queryStrategyByIsLiked(true);

        adapter = new LikeAdapter(context);
        adapter.setStrategies(strategyList);
        listView.setAdapter(adapter);
        listView.setEmptyView(textView);

        // 点击收藏的攻略, 跳转到攻略详情页面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, StrategyDetailsActivity.class);
                intent.putExtra(GiftSayValues.INTENT_STRATEGY_DETAILS_URL, strategyList.get(position).getUrl());
                context.startActivity(intent);

            }
        });

    }


    private void registerReceiver() {
        likeChangedReceiver = new LikeChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GiftSayValues.LIKE_CHANGED_RECEIVER);
        context.registerReceiver(likeChangedReceiver, filter);
    }

    // 接收礼物详情页发出的广播
    class LikeChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 重新获取一次已收藏的攻略
            List<Strategy> strategyList = greenDaoTool.queryStrategyByIsLiked(true);
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
