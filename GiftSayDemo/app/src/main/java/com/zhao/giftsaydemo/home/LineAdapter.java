package com.zhao.giftsaydemo.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;
import com.zhao.giftsaydemo.home.channels.ChannelsFragment;
import com.zhao.giftsaydemo.home.channels.FirstFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/10.
 */
public class LineAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    ArrayList<String> titles;

    HomeChannelsBean bean;

    public void setBean(HomeChannelsBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
        notifyDataSetChanged();
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public LineAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (bean == null) {
            Log.d("LineAdapter", "嗷嗷");
            return new FirstFragment();

        }

//        Log.d("LineAdapter", this.bean.getMessage());
        return ChannelsFragment.newInstance(bean);
        //return fragments.get(position);
    }


//    @Override
//    public Fragment getItem(int position) {
//        return ChannelsFragment.newInstance(position);
//    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position) ;
    }
}
