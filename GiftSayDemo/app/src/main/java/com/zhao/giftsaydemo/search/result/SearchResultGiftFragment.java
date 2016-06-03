package com.zhao.giftsaydemo.search.result;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.gift.channels.GiftChannelsAdapter;
import com.zhao.giftsaydemo.category.gift.channels.GiftChannelsBean;
import com.zhao.giftsaydemo.category.gift.details.GiftDetailsActivity;
import com.zhao.giftsaydemo.util.VolleySingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/28.
 */
@BindContent(R.layout.fragment_pop)
public class SearchResultGiftFragment extends BaseFragment implements GiftChannelsAdapter.OnClickListener {
    @BindView(R.id.fragment_pop_rv)
    private RecyclerView recyclerView;
    private GiftChannelsAdapter adapter;
    private GiftChannelsBean giftChannelsBean;

    @Override
    public void initData() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Input", Context.MODE_PRIVATE);
        String input = sharedPreferences.getString("input", "");

        // 拼接网址获取数据
        String url = "http://api.liwushuo.com/v2/search/item?keyword=" + input + "&limit=20&offset=0&sort=";
        VolleySingle.addRequest(url, GiftChannelsBean.class, new Response.Listener<GiftChannelsBean>() {
            @Override
            public void onResponse(GiftChannelsBean response) {
                giftChannelsBean = response;
                adapter.setBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        adapter = new GiftChannelsAdapter(context);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);


    }

    @Override
    public void onClick(GiftChannelsBean bean, int pos) {
        Intent intent = new Intent(context, GiftDetailsActivity.class);
        GiftChannelsBean.DataBean.ItemsBean data = bean.getData().getItems().get(pos);
        data.setUrl("http://www.liwushuo.com/items/" + giftChannelsBean.getData().getItems().get(pos).getId());
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add(giftChannelsBean.getData().getItems().get(pos).getCover_image_url());
        data.setImage_urls(imgUrls);
        data.setPurchase_url("");
        intent.putExtra("tag", 1);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
