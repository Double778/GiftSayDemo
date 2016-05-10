package com.zhao.giftsaydemo.home.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/10.
 */
@BindContent(R.layout.page_home)
public class FirstFragment extends BaseFragment{
    @BindView(R.id.page_home_rv)
    RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    @Override
    public void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        dataAdapter = new DataAdapter(context);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add("---" + i);
        }
        dataAdapter.setStrings(strings);
        recyclerView.setAdapter(dataAdapter);

    }
}
