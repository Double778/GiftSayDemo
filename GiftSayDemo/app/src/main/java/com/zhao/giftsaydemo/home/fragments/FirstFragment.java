package com.zhao.giftsaydemo.home.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.home.bean.HomeSelectionData;
import com.zhao.giftsaydemo.home.bean.TestBean;
import com.zhao.giftsaydemo.util.MyRequestQueue;
import com.zhao.giftsaydemo.volley.GsonResquest;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/10.
 */
@BindContent(R.layout.page_home)
public class FirstFragment extends BaseFragment {
    @BindView(R.id.page_home_lv)
    private ListView listView;
    private BeanAdapter adapter;

    @Override
    public void initData() {

        getData();

        addHeadView();


        listView.setAdapter(adapter);


    }

    public void addHeadView() {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_user, null);
        listView.addHeaderView(v);

        View view = LayoutInflater.from(context).inflate(R.layout.head_view_horizontal_sv, null);
        int[] ids = {R.id.head_view_horizontal_iv_1, R.id.head_view_horizontal_iv_2, R.id.head_view_horizontal_iv_3, R.id.head_view_horizontal_iv_4, R.id.head_view_horizontal_iv_5, R.id.head_view_horizontal_iv_6, R.id.head_view_horizontal_iv_7, R.id.head_view_horizontal_iv_8};
        for (int i = 0; i < ids.length; i++) {
            ((ImageView) view.findViewById(ids[i])).setImageResource(R.mipmap.ic_launcher);
        }
        listView.addHeaderView(view);


    }

    public void getData() {

        GsonResquest<HomeSelectionData> selectionDataGsonResquest = new GsonResquest<>(Request.Method.GET, "http://api.liwushuo.com/v2/channels/103/items?", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new Response.Listener<HomeSelectionData>() {
            @Override
            public void onResponse(HomeSelectionData response) {
                adapter = new BeanAdapter(context);
                adapter.setData(response);

            }
        }, HomeSelectionData.class);
        MyRequestQueue.getInstance().add(selectionDataGsonResquest);
    }
}
