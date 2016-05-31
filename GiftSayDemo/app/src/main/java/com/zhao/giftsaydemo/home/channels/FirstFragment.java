package com.zhao.giftsaydemo.home.channels;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private LinearLayout layout;
    private List<ImageView> dotViewList;
    private ViewPager viewPager;

    private int currentItem = 0;//当前页面

    boolean isAutoPlay = true;//是否自动轮播

    private ScheduledExecutorService scheduledExecutorService;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == 100) {
                viewPager.setCurrentItem(currentItem);
            }
        }

    };
    private List<String> imgUrls;
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

                imgUrls = new ArrayList<>();
                List<Integer> ids = new ArrayList<>();
                for (int i = 0; i < response.getData().getBanners().size(); i++) {
                    imgUrls.add(response.getData().getBanners().get(i).getImage_url());
                    ids.add(response.getData().getBanners().get(i).getTarget_id());
                }

                View view = LayoutInflater.from(context).inflate(R.layout.head_view_slide_show, null);
                viewPager = (ViewPager) view.findViewById(R.id.head_view_slide_show_vp);
                layout = (LinearLayout) view.findViewById(R.id.dotLayout);
                layout.removeAllViews();
                initView(imgUrls);
                SlideShowAdapter adapter = new SlideShowAdapter(context);
                adapter.setImageUrls(imgUrls);
                adapter.setIds(ids);
                adapter.setSlideShowBean(response);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(0);
                viewPager.addOnPageChangeListener(new MyPageChangeListener());

                if (isAutoPlay) {
                    startPlay();
                }

                listView.addHeaderView(view);


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






    private void initView(List<String> imgUrls) {
        dotViewList = new ArrayList<>();

        for (int i = 0; i < imgUrls.size(); i++) {
            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

            params.leftMargin = 15;//设置小圆点的外边距
            params.rightMargin = 15;

            params.height = 20;//设置小圆点的大小
            params.width = 20;

            if (i == 0) {
                dotView.setBackgroundResource(R.mipmap.point_pressed);
            } else {

                dotView.setBackgroundResource(R.mipmap.point_unpressed);
            }
            layout.addView(dotView, params);

            dotViewList.add(dotView);
            //上面是动态添加了四个小圆点
        }
    }

    /**
     * 开始轮播图切换
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
        //根据他的参数说明，第一个参数是执行的任务，第二个参数是第一次执行的间隔，第三个参数是执行任务的周期；
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imgUrls.size();
                handler.sendEmptyMessage(100);
            }
        }
    }

    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    System.out.println(" 手势滑动，空闲中");
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    System.out.println(" 界面切换中");
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                        System.out.println(" 滑动到最后一张");
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                        System.out.println(" 滑动到第一张");
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
            //这里面动态改变小圆点的被背景，来实现效果
            currentItem = pos;
            for (int i = 0; i < dotViewList.size(); i++) {
                if (i == pos) {
                    ((View) dotViewList.get(pos)).setBackgroundResource(R.mipmap.point_pressed);
                } else {
                    ((View) dotViewList.get(i)).setBackgroundResource(R.mipmap.point_unpressed);
                }
            }
        }

    }


}
