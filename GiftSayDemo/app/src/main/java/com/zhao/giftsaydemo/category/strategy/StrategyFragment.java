package com.zhao.giftsaydemo.category.strategy;

import android.widget.ListView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.volley.GsonRequest;

/**
 * Created by 华哥哥 on 16/5/14.
 */
@BindContent(R.layout.fragment_category_strategy_fragment)
public class StrategyFragment extends BaseFragment{
    @BindView(R.id.fragment_category_strategy_fragment__lv)
    private ListView listView;
    @Override
    public void initData() {

    }
}
