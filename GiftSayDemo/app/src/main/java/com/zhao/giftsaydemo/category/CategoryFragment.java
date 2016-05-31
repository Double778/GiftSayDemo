package com.zhao.giftsaydemo.category;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.strategy.StrategyFragment;
import com.zhao.giftsaydemo.category.gift.GiftFragment;
import com.zhao.giftsaydemo.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by 华哥哥 on 16/5/9.
 * 分类页面
 */
@BindContent(R.layout.fragment_category)
public class CategoryFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.fragment_category_tab)
    TabLayout tabLayout;
    @BindView(R.id.fragment_category_vp)
    ViewPager viewPager;
    @BindView(R.id.fragment_category_search_iv)
    ImageView imageView;

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
        // tab文字颜色
        tabLayout.setTabTextColors(Color.parseColor("#db3746"), Color.WHITE);
        // tab索引条高度
        tabLayout.setSelectedTabIndicatorHeight(0);

        imageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }
}
