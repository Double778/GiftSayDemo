package com.zhao.giftsaydemo.pop;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.gift.details.GiftDetailsActivity;
import com.zhao.giftsaydemo.util.VolleySingle;

/**
 * Created by 华哥哥 on 16/5/9.
 * 热门页面
 */
@BindContent(R.layout.fragment_pop)
public class PopFragment extends BaseFragment implements PopAdapter.OnClickListener {
    @BindView(R.id.fragment_pop_rv)
    private RecyclerView recyclerView;

    private PopAdapter adapter;

    @Override
    public void initData() {

        // 获取数据
        VolleySingle.addRequest("http://api.liwushuo.com/v2/items?gender=1&generation=4&limit=50&oddset=0", PopBean.class, new Response.Listener<PopBean>() {
            @Override
            public void onResponse(PopBean response) {
                adapter.setPopBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        adapter = new PopAdapter(context);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);

    }


    // 点击跳转礼物详情页面
    @Override
    public void onClick(int pos, PopBean bean) {
        PopBean.DataBean.ItemsBean.DataBean1 data = bean.getData().getItems().get(pos).getData();
        Log.d("PopFragment", data.getName());
        Intent intent = new Intent(context, GiftDetailsActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);

    }

}
