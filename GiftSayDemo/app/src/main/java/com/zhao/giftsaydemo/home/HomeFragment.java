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

/**
 * Created by 华哥哥 on 16/5/9.
 */
@BindContent(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    @BindView(R.id.fragment_home_tab)
    private TabLayout tabLayout;
    @BindView(R.id.fragment_home_vp)
    private ViewPager viewPager;
    private HomeAdapter adapter;
    private TabBean tabBean;

    @Override
    public void initData() {
        adapter = new HomeAdapter(getChildFragmentManager());

        requestTitle();


        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


    }

    private void requestTitle() {
        VolleySingle.addRequest("http://api.liwushuo.com/v2/channels/preset?gender=1&generation=4", TabBean.class, new Response.Listener<TabBean>() {
            @Override
            public void onResponse(TabBean response) {
                tabBean = response;

                adapter.setTabBean(tabBean);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
}
