package com.zhao.giftsaydemo.category.strategy;

import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.util.MyRequestQueue;
import com.zhao.giftsaydemo.util.GsonRequest;

/**
 * Created by 华哥哥 on 16/5/14.
 */
@BindContent(R.layout.fragment_category_strategy_fragment)
public class StrategyFragment extends BaseFragment {
    @BindView(R.id.fragment_category_strategy_fragment_lv)
    private ListView listView;
    private StrategyAdapter adapter;

    @Override
    public void initData() {

        GsonRequest<StrategyBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://api.liwushuo.com/v2/channel_groups/all", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new Response.Listener<StrategyBean>() {
            @Override
            public void onResponse(StrategyBean response) {
                adapter = new StrategyAdapter(context);
                listView.setAdapter(adapter);
                adapter.setBean(response);

            }
        }, StrategyBean.class);
        MyRequestQueue.getInstance().add(gsonRequest);


    }


}
