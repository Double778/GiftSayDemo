package com.zhao.giftsaydemo.home.channels;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.GreenDaoTool;
import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;
import com.zhao.giftsaydemo.home.bean.TabBean;
import com.zhao.giftsaydemo.util.SwipeRefreshLoadingLayout;
import com.zhao.giftsaydemo.util.VolleySingle;


/**
 * Created by 华哥哥 on 16/5/13.
 * 复用Fragment
 */
@BindContent(R.layout.fragment_home_channels_fragment)
public class ChannelsFragment extends BaseFragment implements SwipeRefreshLoadingLayout.OnRefreshListener, SwipeRefreshLoadingLayout.OnLoadListener {
    @BindView(R.id.page_home_lv)
    private ListView listView;
    @BindView(R.id.refresh_loading)
    private SwipeRefreshLoadingLayout swipeRefreshLoadingLayout;

    private HomeChannelsBean bean;
    private ChannelsAdapter adapter;
    private GreenDaoTool greenDaoTool;
    private String url;
    private int channels;
    private LikeChangedReceiver likeChangedReceiver;


    public static ChannelsFragment newInstance(int pos, TabBean tabBean) {
        // 穿入的pos, Tab数据放入Bundle中
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putParcelable("TabBean", tabBean);
        ChannelsFragment fragment = new ChannelsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initData() {


        greenDaoTool = new GreenDaoTool();

        // 取出Bundle中的数据
        Bundle args = getArguments();
        int pos = args.getInt("pos");
        TabBean tabBean = args.getParcelable("TabBean");

        // 根据pos,Tab数据获取当前页的数据
        url = "http://api.liwushuo.com/v2/channels/" + tabBean.getData().getChannels().get(pos).getId() + "/items?limit=20&ad=2&gender=2&offset=0&generation=1";
        channels = tabBean.getData().getChannels().get(pos).getId();

        getDate(url, channels);

        adapter = new ChannelsAdapter(context);
        listView.setAdapter(adapter);

        swipeRefreshLoadingLayout.setOnRefreshListener(this);
        swipeRefreshLoadingLayout.setOnLoadListener(this);
        registerReceiver();

    }

    // 注册广播
    private void registerReceiver() {
        likeChangedReceiver = new LikeChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zhao.giftsaydemo.LikeChanged");
        context.registerReceiver(likeChangedReceiver, filter);
    }


    // 上拉加载
    @Override
    public void onRefresh() {
        greenDaoTool.clean();

        getDate(url, channels);
        Toast.makeText(context, "刷新数据", Toast.LENGTH_SHORT).show();
        swipeRefreshLoadingLayout.setRefreshing(false);

    }


    // 下拉刷新
    @Override
    public void onLoad() {
        if (bean.getData().getPaging().getNext_url() != null) {
            getDate(bean.getData().getPaging().getNext_url(), channels);

        } else {
            Toast.makeText(context, "没有更多", Toast.LENGTH_SHORT).show();
        }

        swipeRefreshLoadingLayout.setLoading(false);


    }

    // 获取数据
    public void getDate(String url, final int channels) {
        VolleySingle.addRequest(url, HomeChannelsBean.class, new Response.Listener<HomeChannelsBean>() {
            @Override
            public void onResponse(HomeChannelsBean response) {
                bean = response;
                for (int i = 0; i < response.getData().getItems().size(); i++) {
                    Strategy strategy = new Strategy(
                            System.currentTimeMillis(),
                            channels,
                            response.getData().getItems().get(i).getTitle(),
                            response.getData().getItems().get(i).getUrl(),
                            response.getData().getItems().get(i).getCover_image_url(),
                            response.getData().getItems().get(i).isLiked(),
                            response.getData().getItems().get(i).getLikes_count());
                    greenDaoTool.addStrategy(strategy);
                }


                adapter.setChannels(channels);
                adapter.setStrategies(greenDaoTool.queryStrategyByChannels(channels));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    // 收藏改变广播
    class LikeChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            adapter.setStrategies(greenDaoTool.queryStrategyByChannels(channels));
        }
    }


    @Override
    public void onDestroy() {

        context.unregisterReceiver(likeChangedReceiver);
        super.onDestroy();
    }


}
