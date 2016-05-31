package com.zhao.giftsaydemo.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.home.bean.TabBean;
import com.zhao.giftsaydemo.util.VolleySingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 * 主页面
 */
@BindContent(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    @BindView(R.id.fragment_home_tab)
    private TabLayout tabLayout;
    @BindView(R.id.fragment_home_vp)
    private ViewPager viewPager;
    private HomeAdapter adapter;

    @Override
    public void initData() {
        adapter = new HomeAdapter(getChildFragmentManager());

        // 获取tab数据
        requestTab();


        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


    }

    private void requestTab() {
        VolleySingle.addRequest("http://api.liwushuo.com/v2/channels/preset?gender=1&generation=4", TabBean.class, new Response.Listener<TabBean>() {
            @Override
            public void onResponse(TabBean response) {
                adapter.setTabBean(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
}
