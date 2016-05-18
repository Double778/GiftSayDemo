package com.zhao.giftsaydemo.category.strategy;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.category.strategy.details.StrategyDetailsAdapter;
import com.zhao.giftsaydemo.category.strategy.details.StrategyDetailsBean;
import com.zhao.giftsaydemo.util.VolleySingle;

/**
 * Created by 华哥哥 on 16/5/17.
 */
@BindContent(R.layout.activity_strategy_details)
public class StrategyDetailsActivity extends BaseActivity{
    @BindView(R.id.aty_strategy_details_lv)
    private ListView listView;
    private StrategyDetailsAdapter adapter;
    @Override
    public void initData() {
        adapter = new StrategyDetailsAdapter(this);

        int id = getIntent().getIntExtra("Id", 0);
        VolleySingle.addRequest("http://api.liwushuo.com/v2/channels/" + id + "/items?limit=20&offset=0", StrategyDetailsBean.class, new Response.Listener<StrategyDetailsBean>() {
            @Override
            public void onResponse(StrategyDetailsBean response) {
                adapter.setData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        listView.setAdapter(adapter);

    }
}
