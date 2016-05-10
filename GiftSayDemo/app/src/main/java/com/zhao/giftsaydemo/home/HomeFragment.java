package com.zhao.giftsaydemo.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.home.fragments.FirstFragment;

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
        fragments = new ArrayList<>();
        for (int i = 0; i < 17; i++) {

            fragments.add(new FirstFragment());
        }


        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);




    }
}
