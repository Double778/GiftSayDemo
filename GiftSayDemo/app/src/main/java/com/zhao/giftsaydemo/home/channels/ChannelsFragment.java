package com.zhao.giftsaydemo.home.channels;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.base.TestFragment;
import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;


/**
 * Created by 华哥哥 on 16/5/13.
 */
@BindContent(R.layout.fragment_home_channels_fragment)
public class ChannelsFragment extends BaseFragment {
    @BindView(R.id.page_home_lv)
    private ListView listView;
    private DetailsAdapter adapter;

    private static final String CHANNELS_DATA = "data";

    public static ChannelsFragment newInstance(HomeChannelsBean homeChannelsBean) {
        Bundle args = new Bundle();
        args.putParcelable(CHANNELS_DATA, homeChannelsBean);

        ChannelsFragment fragment = new ChannelsFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void initData() {
        Bundle args = getArguments();
        HomeChannelsBean homeChannelsBean = args.getParcelable(CHANNELS_DATA);
        adapter = new DetailsAdapter(context);
        adapter.setData(homeChannelsBean);
        listView.setAdapter(adapter);
    }


}
