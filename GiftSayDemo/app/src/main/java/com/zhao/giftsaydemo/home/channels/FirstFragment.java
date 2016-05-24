package com.zhao.giftsaydemo.home.channels;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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
import com.zhao.giftsaydemo.util.SwipeRefreshLoadingLayout;
import com.zhao.giftsaydemo.util.VolleySingle;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/10.
 */
@BindContent(R.layout.fragment_home_channels_fragment)
public class FirstFragment extends BaseFragment implements SwipeRefreshLoadingLayout.OnRefreshListener, SwipeRefreshLoadingLayout.OnLoadListener {
    @BindView(R.id.page_home_lv)
    private ListView listView;
    private DetailsAdapter adapter;

    @BindView(R.id.refresh_loading)
    private SwipeRefreshLoadingLayout swipeRefreshLoadingLayout;
    private HomeChannelsBean bean;


    @Override
    public void initData() {
        adapter = new DetailsAdapter(context);

        getData();

        addHeadView();


        listView.setAdapter(adapter);

        swipeRefreshLoadingLayout.setOnRefreshListener(this);
        swipeRefreshLoadingLayout.setOnLoadListener(this);


    }

    public void addHeadView() {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_user, null);
        listView.addHeaderView(v);

        VolleySingle.addRequest("http://api.liwushuo.com/v2/secondary_banners?gender=2&generation=1", HomeSubjectBean.class, new Response.Listener<HomeSubjectBean>() {
            @Override
            public void onResponse(HomeSubjectBean response) {
                View view = LayoutInflater.from(context).inflate(R.layout.head_view_horizontal_sv, null);
                int[] ids = {R.id.head_view_horizontal_iv_1, R.id.head_view_horizontal_iv_2, R.id.head_view_horizontal_iv_3, R.id.head_view_horizontal_iv_4, R.id.head_view_horizontal_iv_5, R.id.head_view_horizontal_iv_6, R.id.head_view_horizontal_iv_7, R.id.head_view_horizontal_iv_8,R.id.head_view_horizontal_iv_9,R.id.head_view_horizontal_iv_10};
                for (int i = 0; i < ids.length; i++) {

                    Picasso.with(context).load(response.getData().getSecondary_banners().get(i).getImage_url()).resize(200,200).into(((ImageView) view.findViewById(ids[i])));
                }
                listView.addHeaderView(view);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



    }

    public void getData() {

        VolleySingle.addRequest("http://api.liwushuo.com/v2/channels/103/items?limit=20&ad=2&gender=2&offset=0&generation=1", HomeChannelsBean.class, new Response.Listener<HomeChannelsBean>() {
            @Override
            public void onResponse(HomeChannelsBean response) {
                bean = response;
                adapter.setData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    // 下拉刷新
    @Override
    public void onRefresh() {
        VolleySingle.addRequest("http://api.liwushuo.com/v2/channels/103/items?limit=20&ad=2&gender=2&offset=0&generation=1", HomeChannelsBean.class, new Response.Listener<HomeChannelsBean>() {
            @Override
            public void onResponse(HomeChannelsBean response) {
                adapter.setData(response);
                Log.d("ChannelsFragment", "刷新");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    // 上拉加载
    @Override
    public void onLoad() {
        if (bean.getData().getPaging().getNext_url() != null) {

            VolleySingle.addRequest(bean.getData().getPaging().getNext_url(), HomeChannelsBean.class, new Response.Listener<HomeChannelsBean>() {
                @Override
                public void onResponse(HomeChannelsBean response) {
                    Log.d("ChannelsFragment", "bean.getData().getItems().size():" + bean.getData().getItems().size());
                    for (int i = 0; i < response.getData().getItems().size(); i++) {
                        bean.getData().getItems().add(response.getData().getItems().get(i));
                    }
                    Log.d("ChannelsFragment", "bean.getData().getItems().size():" + bean.getData().getItems().size());

                    adapter.setData(bean);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } else {
            Toast.makeText(context, "没有更多", Toast.LENGTH_SHORT).show();
        }
    }
}
