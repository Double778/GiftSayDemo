package com.zhao.giftsaydemo.home.channels;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.base.TestFragment;
import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;
import com.zhao.giftsaydemo.home.bean.TabBean;
import com.zhao.giftsaydemo.util.VolleySingle;


/**
 * Created by 华哥哥 on 16/5/13.
 */
@BindContent(R.layout.fragment_home_channels_fragment)
public class ChannelsFragment extends BaseFragment {
    @BindView(R.id.page_home_lv)
    private ListView listView;
    private DetailsAdapter adapter;


    public static ChannelsFragment newInstance(int pos, TabBean tabBean) {
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putParcelable("TabBean", tabBean);
        ChannelsFragment fragment = new ChannelsFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void initData() {
        Bundle args = getArguments();
        int pos = args.getInt("pos");
        TabBean tabBean = args.getParcelable("TabBean");
        String path = "http://api.liwushuo.com/v2/channels/" + tabBean.getData().getChannels().get(pos).getId() + "/items?limit=20&ad=2&gender=2&offset=0&generation=1";
        VolleySingle.addRequest(path, HomeChannelsBean.class, new Response.Listener<HomeChannelsBean>() {
            @Override
            public void onResponse(HomeChannelsBean response) {
                adapter = new DetailsAdapter(context);
                adapter.setData(response);
                listView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }


}
