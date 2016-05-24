package com.zhao.giftsaydemo.category.gift.channels;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.category.gift.details.GiftDetailsActivity;
import com.zhao.giftsaydemo.util.VolleySingle;


/**
 * Created by 华哥哥 on 16/5/19.
 */
@BindContent(R.layout.activity_gift_channels)
public class GiftChannelsActivity extends BaseActivity implements View.OnClickListener, GiftChannelsAdapter.OnClickListener {
    @BindView(R.id.activity_gift_channels_rv)
    private RecyclerView recyclerView;
    private GiftChannelsAdapter adapter;

    @Override
    public void initData() {

        setTitle();

        String url = "http://api.liwushuo.com/v2/item_subcategories/" + getIntent().getIntExtra("Id", 0) + "/items?limit=20&offset=20";
        VolleySingle.addRequest(url, GiftChannelsBean.class, new Response.Listener<GiftChannelsBean>() {
            @Override
            public void onResponse(GiftChannelsBean response) {
                adapter.setBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        adapter = new GiftChannelsAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);

    }

    private void setTitle() {
        findViewById(R.id.title_name_iv).setVisibility(View.INVISIBLE);
        findViewById(R.id.title_right_iv).setVisibility(View.INVISIBLE);
        ImageView imageView = (ImageView) findViewById(R.id.title_left_iv);
        imageView.setImageResource(R.mipmap.ic_action_back);
        imageView.setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.title_name_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setTextSize(16);
        textView.setText(getIntent().getStringExtra("name"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_iv:
                finish();
                break;
        }
    }


    @Override
    public void onClick(GiftChannelsBean bean, int pos) {
        Intent intent = new Intent(this, GiftDetailsActivity.class);
        GiftChannelsBean.DataBean.ItemsBean data = bean.getData().getItems().get(pos);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
