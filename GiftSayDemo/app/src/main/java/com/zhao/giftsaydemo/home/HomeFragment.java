package com.zhao.giftsaydemo.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 */
@BindContent(R.layout.fragment_home)
public class HomeFragment extends BaseFragment{
    @BindView(R.id.fragment_home_tab)
    private TabLayout tabLayout;
    @BindView(R.id.fragment_home_vp)
    private ViewPager viewPager;
    private MyAdapter adapter;


    @Override
    public void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("aa");
        stringList.add("bb");
        adapter = new MyAdapter(context);
        adapter.setStringList(stringList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
