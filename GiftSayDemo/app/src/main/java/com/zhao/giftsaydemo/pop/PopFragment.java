package com.zhao.giftsaydemo.pop;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 */
@BindContent(R.layout.fragment_pop)
public class PopFragment extends BaseFragment{
    @BindView(R.id.fragment_pop_rv)
    private RecyclerView recyclerView;

    private PopAdapter adapter;
    @Override
    public void initData() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<Integer> drawables = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            drawables.add(R.mipmap.ic_launcher);
        }
        adapter = new PopAdapter(context);
        adapter.setDrawables(drawables);
        recyclerView.setAdapter(adapter);
    }
}
