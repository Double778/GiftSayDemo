package com.zhao.giftsaydemo.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhao.giftsaydemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by 华哥哥 on 16/5/9.
 */
public class MyAdapter extends PagerAdapter{
    List<String> stringList;
    private Context context;
    List<String> strings;
    private DataAdapter dataAdapter;



    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return stringList == null? 0: stringList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.page_home, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.page_home_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        dataAdapter = new DataAdapter(context);
        strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add("" + i);
        }
        dataAdapter.setStrings(strings);
        Log.d("MyAdapter", "strings:" + strings);
        recyclerView.setAdapter(dataAdapter);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
