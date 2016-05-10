package com.zhao.giftsaydemo.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.zhao.giftsaydemo.home.bean.TabBean;
import com.zhao.giftsaydemo.volley.VolleyTool;

import java.util.List;

/**
 * Created by 华哥哥 on 16/5/10.
 */
public class LineAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    private VolleyTool volleyTool;
    String[] strings = {"精选", "一周最热", "天天种草", "完美礼物", "谈个恋爱", "送女票", "送爸妈", "送基友", "送闺蜜", "送同事", "送宝贝", "设计感", "科技范", "创意生活", "文艺风", "奇葩搞怪", "萌萌哒"};


    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public LineAdapter(FragmentManager fm) {
        super(fm);
        volleyTool = new VolleyTool();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

//    @Override
    public CharSequence getPageTitle(int position) {
//      TabBean tabBean =  volleyTool.JsonTabData("http://api.liwushuo.com/v2/channels/preset?gender=1&generation=4");
//       Log.d("LineAdapter", "tabBean:" + tabBean);
//       return tabBean.getData().getChannels().get(position).getName();
        return strings[position];
    }
}
