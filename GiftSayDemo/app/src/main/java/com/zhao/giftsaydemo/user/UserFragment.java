package com.zhao.giftsaydemo.user;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyDetailsActivity;
import com.zhao.giftsaydemo.db.GreenDaoSingle;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDao;

import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 */
@BindContent(R.layout.fragment_user)
public class UserFragment extends BaseFragment{
    @BindView(R.id.fragment_user_lv)
    private ListView listView;
    private LikeStrategyAdapter adapter;
    private StrategyDao strategyDao;
    @Override
    public void initData() {
        strategyDao = GreenDaoSingle.getInstance().getStrategyDao();
        final List<Strategy> strategyList = strategyDao.queryBuilder().list();

        adapter = new LikeStrategyAdapter(context);
        adapter.setStrategies(strategyList);
        listView.setAdapter(adapter);

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
}
