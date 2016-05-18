package com.zhao.giftsaydemo.category;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.strategy.StrategyFragment;
import com.zhao.giftsaydemo.category.gift.GiftFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 */
@BindContent(R.layout.fragment_category)
public class CategoryFragment extends BaseFragment{

    @BindView(R.id.fragment_category_tab)
    TabLayout tabLayout;
    @BindView(R.id.fragment_category_vp)
    ViewPager viewPager;

    @Override
    public void initData() {
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StrategyFragment());
        fragments.add(new GiftFragment());

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            String[] titles ={"攻略","礼物"};
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments == null? 0: fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#db3746"), Color.WHITE);
        tabLayout.setSelectedTabIndicatorHeight(0);

    }
}
