package com.zhao.giftsaydemo.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;
import com.zhao.giftsaydemo.home.bean.TabBean;
import com.zhao.giftsaydemo.home.channels.ChannelsFragment;
import com.zhao.giftsaydemo.home.channels.FirstFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/10.
 */
public class HomeAdapter extends FragmentPagerAdapter {
    private ArrayList<String> titles;

    private TabBean tabBean;

    public void setTabBean(TabBean tabBean) {
        this.tabBean = tabBean;
        titles = new ArrayList<>();
        for (int i = 0; i < tabBean.getData().getChannels().size(); i++) {
            titles.add(tabBean.getData().getChannels().get(i).getName());
        }
        notifyDataSetChanged();
    }


    public HomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FirstFragment();

        }
        return ChannelsFragment.newInstance(position, tabBean);
    }


    @Override
    public int getCount() {
        return titles != null && titles.size() > 0 ? titles.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
