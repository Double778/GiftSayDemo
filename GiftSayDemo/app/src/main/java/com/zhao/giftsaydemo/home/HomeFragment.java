package com.zhao.giftsaydemo.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;
import com.zhao.giftsaydemo.home.bean.TabBean;
import com.zhao.giftsaydemo.util.MyRequestQueue;
import com.zhao.giftsaydemo.volley.GsonRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 */
@BindContent(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    @BindView(R.id.fragment_home_tab)
    private TabLayout tabLayout;
    @BindView(R.id.fragment_home_vp)
    private ViewPager viewPager;
    private LineAdapter adapter;
    private TabBean tabBean;
    @Override
    public void initData() {
        adapter = new LineAdapter(getChildFragmentManager());

        requestTitle();

//        GsonRequest<HomeChannelsBean> selectionDataGsonRequest = new GsonRequest<>(Request.Method.GET, "http://api.liwushuo.com/v2/channels/103/items?", new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }, new Response.Listener<HomeChannelsBean>() {
//            @Override
//            public void onResponse(HomeChannelsBean response) {
//                if (response != null) {
//                  String title1 = response.getData().getItems().get(0).getTitle();
//
//                    Log.d("HomeFragment", "---->" + title1);
//
//                    Log.d("HomeFragment",   "---->" + response.getData().toString() );
//
//
//                    adapter.setBean(response);
//                }
//
//            }
//
//        }, HomeChannelsBean.class);
//        MyRequestQueue.getInstance().add(selectionDataGsonRequest);


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("HomeFragment", "position:" + position);
                String path = "http://api.liwushuo.com/v2/channels/" + tabBean.getData().getCandidates().get(position).getId() + "/items?limit=20&ad=2&gender=2&offset=0&generation=1";
                GsonRequest<HomeChannelsBean> homeChannelsBeanGsonRequest = new GsonRequest<>(Request.Method.GET, path, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, new Response.Listener<HomeChannelsBean>() {
                    @Override
                    public void onResponse(HomeChannelsBean response) {
                        Log.d("HomeFragment", response.getData().getItems().get(0).getTitle());
                        adapter.setBean(response);

                    }
                }, HomeChannelsBean.class);
                MyRequestQueue.getInstance().add(homeChannelsBeanGsonRequest);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void requestTitle() {
        GsonRequest<TabBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://api.liwushuo.com/v2/channels/preset?gender=1&generation=4", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new Response.Listener<TabBean>() {
            @Override
            public void onResponse(TabBean response) {
                tabBean = response;
                if (response.getData().getChannels().size() != 0) {

                    ArrayList<String> titles = new ArrayList<>();
                    for (int i = 0; i < response.getData().getChannels().size(); i++) {
                        titles.add(response.getData().getChannels().get(i).getName());
                    }
                    adapter.setTitles(titles);
                    String path = "http://api.liwushuo.com/v2/channels/" + tabBean.getData().getCandidates().get(0).getId() + "/items?limit=20&ad=2&gender=2&offset=0&generation=1";
                    GsonRequest<HomeChannelsBean> homeChannelsBeanGsonRequest = new GsonRequest<>(Request.Method.GET, path, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }, new Response.Listener<HomeChannelsBean>() {
                        @Override
                        public void onResponse(HomeChannelsBean response) {
                            Log.d("HomeFragment", response.getData().getItems().get(1).getTitle());
                            adapter.setBean(response);

                        }
                    }, HomeChannelsBean.class);
                    MyRequestQueue.getInstance().add(homeChannelsBeanGsonRequest);


                }
            }
        }, TabBean.class);
        MyRequestQueue.getInstance().add(gsonRequest);
    }
}
