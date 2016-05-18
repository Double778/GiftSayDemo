package com.zhao.giftsaydemo.home.channels;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;
import com.zhao.giftsaydemo.home.bean.HomeSubjectBean;
import com.zhao.giftsaydemo.util.MyRequestQueue;
import com.zhao.giftsaydemo.util.GsonRequest;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/10.
 */
@BindContent(R.layout.fragment_home_channels_fragment)
public class FirstFragment extends BaseFragment {
    @BindView(R.id.page_home_lv)
    private ListView listView;
    private DetailsAdapter adapter;

    @Override
    public void initData() {
        adapter = new DetailsAdapter(context);

        getData();

        addHeadView();


        listView.setAdapter(adapter);


    }

    public void addHeadView() {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_user, null);
        listView.addHeaderView(v);




    }

    public void getData() {

        GsonRequest<HomeChannelsBean> selectionDataGsonRequest = new GsonRequest<>(Request.Method.GET, "http://api.liwushuo.com/v2/channels/103/items?", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new Response.Listener<HomeChannelsBean>() {
            @Override
            public void onResponse(HomeChannelsBean response) {
                adapter.setData(response);

            }
        }, HomeChannelsBean.class);
        MyRequestQueue.getInstance().add(selectionDataGsonRequest);
        GsonRequest<HomeSubjectBean> subjectBeanGsonRequest = new GsonRequest<>(Request.Method.GET, "http://api.liwushuo.com/v2/secondary_banners?gender=2&generation=1", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new Response.Listener<HomeSubjectBean>() {
            @Override
            public void onResponse(HomeSubjectBean response) {
                View view = LayoutInflater.from(context).inflate(R.layout.head_view_horizontal_sv, null);
                int[] ids = {R.id.head_view_horizontal_iv_1, R.id.head_view_horizontal_iv_2, R.id.head_view_horizontal_iv_3, R.id.head_view_horizontal_iv_4, R.id.head_view_horizontal_iv_5, R.id.head_view_horizontal_iv_6, R.id.head_view_horizontal_iv_7, R.id.head_view_horizontal_iv_8,R.id.head_view_horizontal_iv_9,R.id.head_view_horizontal_iv_10};
                for (int i = 0; i < ids.length; i++) {

                    Picasso.with(context).load(response.getData().getSecondary_banners().get(i).getImage_url()).resize(200,200).into(((ImageView) view.findViewById(ids[i])));
                }
                listView.addHeaderView(view);
            }
        }, HomeSubjectBean.class);
        MyRequestQueue.getInstance().add(subjectBeanGsonRequest);
    }
}
