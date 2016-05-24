package com.zhao.giftsaydemo.category.gift.details;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by 华哥哥 on 16/5/20.
 */
public class GiftDetailsContentAdapter extends FragmentPagerAdapter{
    private String[] titles = {"图文介绍", "评论"};
    public GiftDetailsContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }
}
