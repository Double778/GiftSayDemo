package com.zhao.giftsaydemo.search.result;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyChannelsAdapter;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyChannelsBean;
import com.zhao.giftsaydemo.category.strategy.subject.SubjectChannelsAdapter;

import com.zhao.giftsaydemo.category.strategy.subject.SubjectChannelsBean;
import com.zhao.giftsaydemo.util.VolleySingle;
import com.zhao.giftsaydemo.value.GiftSayValues;

/**
 * Created by 华哥哥 on 16/5/28.
 */
@BindContent(R.layout.fragment_category_strategy_fragment)
public class SearchResultStrategyFragment extends BaseFragment {
    @BindView(R.id.fragment_category_strategy_fragment_lv)
    private ListView listView;
    private SubjectChannelsAdapter adapter;
    @Override
    public void initData() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(GiftSayValues.SP_NAME, Context.MODE_PRIVATE);
        String input = sharedPreferences.getString(GiftSayValues.SEARCH_INPUT, "");
        // 获取数据
        VolleySingle.addRequest("http://api.liwushuo.com/v2/search/post?keyword=" + input + "&limit=20&offset=0&sort=", SubjectChannelsBean.class, new Response.Listener<SubjectChannelsBean>() {
            @Override
            public void onResponse(SubjectChannelsBean response) {
                adapter.setData(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        adapter = new SubjectChannelsAdapter(context);
        listView.setAdapter(adapter);

    }

}
