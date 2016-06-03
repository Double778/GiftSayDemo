package com.zhao.giftsaydemo.search.result;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.home.channels.FirstFragment;
import com.zhao.giftsaydemo.pop.PopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/28.
 */
@BindContent(R.layout.fragment_search_result)
public class SearchResultFragment extends BaseFragment{
    @BindView(R.id.fragment_search_result_vp)
    private ViewPager viewPager;
    @BindView(R.id.fragment_search_result_tab)
    private TabLayout tabLayout;

    @Override
    public void initData() {
        SearchResultAdapter adapter = new SearchResultAdapter(getChildFragmentManager());
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new SearchResultGiftFragment());
        fragments.add(new SearchResultStrategyFragment());
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
