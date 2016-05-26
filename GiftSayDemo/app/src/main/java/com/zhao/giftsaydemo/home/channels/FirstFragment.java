package com.zhao.giftsaydemo.home.channels;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
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
import com.zhao.giftsaydemo.category.gift.details.GiftDetailsHeadAdapter;
import com.zhao.giftsaydemo.db.GreenDaoSingle;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDao;
import com.zhao.giftsaydemo.db.StrategyDaoTool;
import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;
import com.zhao.giftsaydemo.home.bean.HomeSubjectBean;

import com.zhao.giftsaydemo.home.bean.SlideShowBean;
import com.zhao.giftsaydemo.util.SwipeRefreshLoadingLayout;
import com.zhao.giftsaydemo.util.VolleySingle;


import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/10.
 * 主页的第一个界面
 */
@BindContent(R.layout.fragment_home_channels_fragment)
public class FirstFragment extends BaseFragment implements SwipeRefreshLoadingLayout.OnRefreshListener, SwipeRefreshLoadingLayout.OnLoadListener {
    @BindView(R.id.page_home_lv)
    private ListView listView;
    private DetailsAdapter adapter;

    // 刷新, 加载组件
    @BindView(R.id.refresh_loading)
    private SwipeRefreshLoadingLayout swipeRefreshLoadingLayout;
    private HomeChannelsBean bean;
    private StrategyDaoTool strategyDaoTool;
    //private LikeChangedReceiver likeChangedReceiver;


    @Override
    public void initData() {

        //registerReceiver();

        strategyDaoTool = new StrategyDaoTool();
        adapter = new DetailsAdapter(context);

        getData("http://api.liwushuo.com/v2/channels/108/items?limit=20&ad=2&gender=2&offset=0&generation=1", 108);

        // 加头布局
        addHeadView();


        listView.setAdapter(adapter);

        swipeRefreshLoadingLayout.setOnRefreshListener(this);
        swipeRefreshLoadingLayout.setOnLoadListener(this);


    }

    public void addHeadView() {

        // 轮播图
        VolleySingle.addRequest("http://api.liwushuo.com/v2/banners", SlideShowBean.class, new Response.Listener<SlideShowBean>() {
            @Override
            public void onResponse(SlideShowBean response) {

                List<String> imgUrls = new ArrayList<>();
                for (int i = 0; i < response.getData().getBanners().size(); i++) {
                    imgUrls.add(response.getData().getBanners().get(i).getImage_url());
                }
                View view = LayoutInflater.from(context).inflate(R.layout.head_view_slide_show, null);
                ViewPager viewPager = (ViewPager) view.findViewById(R.id.head_view_slide_show_vp);
                GiftDetailsHeadAdapter adapter = new GiftDetailsHeadAdapter(context);
                adapter.setImageUrls(imgUrls);
                viewPager.setAdapter(adapter);

                listView.addHeaderView(view);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        // 专题头布局
        VolleySingle.addRequest("http://api.liwushuo.com/v2/secondary_banners?gender=2&generation=1", HomeSubjectBean.class, new Response.Listener<HomeSubjectBean>() {
            @Override
            public void onResponse(HomeSubjectBean response) {
                View view = LayoutInflater.from(context).inflate(R.layout.head_view_horizontal_sv, null);
                int[] ids = {R.id.head_view_horizontal_iv_1, R.id.head_view_horizontal_iv_2, R.id.head_view_horizontal_iv_3, R.id.head_view_horizontal_iv_4, R.id.head_view_horizontal_iv_5, R.id.head_view_horizontal_iv_6, R.id.head_view_horizontal_iv_7, R.id.head_view_horizontal_iv_8, R.id.head_view_horizontal_iv_9, R.id.head_view_horizontal_iv_10};
                for (int i = 0; i < ids.length; i++) {

                    Picasso.with(context).load(response.getData().getSecondary_banners().get(i).getImage_url()).resize(200, 200).into(((ImageView) view.findViewById(ids[i])));
                }
                listView.addHeaderView(view);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


    }


    // 请求数据方法
    public void getData(String url, final int channels) {

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
                    // 获取完添加进数据库
                    strategyDaoTool.addStrategy(strategy);
                }

                adapter.setChannels(channels);
                // 根据channels从数据库获取数据
                adapter.setStrategies(strategyDaoTool.queryStrategyByChannels(channels));
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
        // 下拉时清理一次数据库
        strategyDaoTool.clean();
        // 重新获取数据
        getData("http://api.liwushuo.com/v2/channels/108/items?limit=20&ad=2&gender=2&offset=0&generation=1", 108);

        Toast.makeText(context, "刷新数据", Toast.LENGTH_SHORT).show();


        swipeRefreshLoadingLayout.setRefreshing(false);

    }

    // 上拉加载
    @Override
    public void onLoad() {
        // 如果nextUrl不为null就获取里面的数据
        if (!bean.getData().getPaging().getNext_url().equals("")) {
            getData(bean.getData().getPaging().getNext_url(), 108);

        } else {
            Toast.makeText(context, "没有更多", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLoadingLayout.setLoading(false);
    }


//    private void registerReceiver() {
//        likeChangedReceiver = new LikeChangedReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.zhao.giftsaydemo.LikeChanged");
//        context.registerReceiver(likeChangedReceiver, filter);
//    }
//
//
//    class LikeChangedReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            Log.d("q", "啊啊啊啊");
//            adapter.setStrategies(strategyDaoTool.queryStrategyByChannels(108));
//        }
//    }

//    @Override
//    public void onDestroy() {
//        context.unregisterReceiver(likeChangedReceiver);
//        super.onDestroy();
//    }
}
