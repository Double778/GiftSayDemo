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
import com.zhao.giftsaydemo.volley.GsonRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 */
@BindContent(R.layout.fragment_pop)
public class PopFragment extends BaseFragment{
    @BindView(R.id.fragment_pop_rv)
    private RecyclerView recyclerView;

    private PopAdapter adapter;
    @Override
    public void initData() {
        adapter = new PopAdapter(context);
        GsonRequest<PopBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://api.liwushuo.com/v2/items?limit=20&offset=0&gender=2&generation=1", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new Response.Listener<PopBean>() {
            @Override
            public void onResponse(PopBean response) {
                adapter.setPopBean(response);
            }
        }, PopBean.class);
        MyRequestQueue.getInstance().add(gsonRequest);

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        List<Integer> drawables = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            drawables.add(R.mipmap.ic_launcher);
        }
        recyclerView.setAdapter(adapter);
    }


}
