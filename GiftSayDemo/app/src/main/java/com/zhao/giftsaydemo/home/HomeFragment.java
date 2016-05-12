package com.zhao.giftsaydemo.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

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
import com.zhao.giftsaydemo.home.bean.TabBean;
import com.zhao.giftsaydemo.home.fragments.FirstFragment;
import com.zhao.giftsaydemo.util.MyRequestQueue;
import com.zhao.giftsaydemo.volley.GsonResquest;

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
    private List<Fragment> fragments;

    @Override
    public void initData() {
        adapter = new LineAdapter(getChildFragmentManager());

        GsonResquest<TabBean> gsonResquest = new GsonResquest<>(Request.Method.GET, "http://api.liwushuo.com/v2/channels/preset?gender=1&generation=4", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new Response.Listener<TabBean>() {
            @Override
            public void onResponse(TabBean response) {
                if (response.getData().getChannels().size() != 0) {


                    ArrayList<String> titles = new ArrayList<>();
                    for (int i = 0; i < response.getData().getChannels().size(); i++) {
                        titles.add(response.getData().getChannels().get(i).getName());
                    }
                    Log.d("HomeFragment", "titles:" + titles);
                    adapter.setTitles(titles);
                }
            }
        }, TabBean.class);
        MyRequestQueue.getInstance().add(gsonResquest);

        fragments = new ArrayList<>();
        for (int i = 0; i < 17; i++) {

            fragments.add(new FirstFragment());
        }


        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
