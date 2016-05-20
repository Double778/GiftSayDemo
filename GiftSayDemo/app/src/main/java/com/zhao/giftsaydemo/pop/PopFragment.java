package com.zhao.giftsaydemo.pop;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.util.MyRequestQueue;
import com.zhao.giftsaydemo.util.GsonRequest;
import com.zhao.giftsaydemo.util.VolleySingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 */
@BindContent(R.layout.fragment_pop)
public class PopFragment extends BaseFragment {
    @BindView(R.id.fragment_pop_rv)
    private RecyclerView recyclerView;

    private PopAdapter adapter;

    @Override
    public void initData() {

        VolleySingle.addRequest("http://api.liwushuo.com/v2/items?limit=20&offset=0&gender=2&generation=1", PopBean.class, new Response.Listener<PopBean>() {
            @Override
            public void onResponse(PopBean response) {
                adapter.setPopBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        adapter = new PopAdapter(context);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);
    }


}
