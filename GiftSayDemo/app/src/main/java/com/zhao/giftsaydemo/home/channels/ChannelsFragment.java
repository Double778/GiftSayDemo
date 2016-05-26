package com.zhao.giftsaydemo.home.channels;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.base.TestFragment;
import com.zhao.giftsaydemo.db.GreenDaoSingle;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDao;
import com.zhao.giftsaydemo.db.StrategyDaoTool;
import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;
import com.zhao.giftsaydemo.home.bean.TabBean;
import com.zhao.giftsaydemo.util.SwipeRefreshLoadingLayout;
import com.zhao.giftsaydemo.util.VolleySingle;

import java.util.List;


/**
 * Created by 华哥哥 on 16/5/13.
 */
@BindContent(R.layout.fragment_home_channels_fragment)
public class ChannelsFragment extends BaseFragment implements SwipeRefreshLoadingLayout.OnRefreshListener, SwipeRefreshLoadingLayout.OnLoadListener {
    @BindView(R.id.page_home_lv)
    private ListView listView;
    @BindView(R.id.refresh_loading)
    private SwipeRefreshLoadingLayout swipeRefreshLoadingLayout;

    private HomeChannelsBean bean;
    private DetailsAdapter adapter;
    private StrategyDaoTool strategyDaoTool;
    private String url;
    private int channels;
    private LikeChangedReceiver likeChangedReceiver;


    public static ChannelsFragment newInstance(int pos, TabBean tabBean) {
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putParcelable("TabBean", tabBean);
        ChannelsFragment fragment = new ChannelsFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void initData() {
        registerReceiver();

        strategyDaoTool = new StrategyDaoTool();

        Bundle args = getArguments();
        int pos = args.getInt("pos");
        TabBean tabBean = args.getParcelable("TabBean");

        url = "http://api.liwushuo.com/v2/channels/" + tabBean.getData().getChannels().get(pos).getId() + "/items?limit=20&ad=2&gender=2&offset=0&generation=1";
        channels = tabBean.getData().getChannels().get(pos).getId();

        getDate(url, channels);

        adapter = new DetailsAdapter(context);
        listView.setAdapter(adapter);

        swipeRefreshLoadingLayout.setOnRefreshListener(this);
        swipeRefreshLoadingLayout.setOnLoadListener(this);


    }

    private void registerReceiver() {
        likeChangedReceiver = new LikeChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zhao.giftsaydemo.LikeChanged");
        context.registerReceiver(likeChangedReceiver, filter);
    }


    @Override
    public void onRefresh() {
        strategyDaoTool.clean();

        getDate(url, channels);
        Toast.makeText(context, "刷新数据", Toast.LENGTH_SHORT).show();
        swipeRefreshLoadingLayout.setRefreshing(false);

    }

    @Override
    public void onLoad() {
        if (bean.getData().getPaging().getNext_url() != null) {
            getDate(bean.getData().getPaging().getNext_url(), channels);

        } else {
            Toast.makeText(context, "没有更多", Toast.LENGTH_SHORT).show();
        }

        swipeRefreshLoadingLayout.setLoading(false);


    }

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
                    strategyDaoTool.addStrategy(strategy);
                }

                adapter.setChannels(channels);
                adapter.setStrategies(strategyDaoTool.queryStrategyByChannels(channels));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    class LikeChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("q", "aa");
            adapter.setStrategies(strategyDaoTool.queryStrategyByChannels(channels));

        }
    }

    @Override
    public void onDestroy() {
        context.unregisterReceiver(likeChangedReceiver);
        super.onDestroy();
    }
}
