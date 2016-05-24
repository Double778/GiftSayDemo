package com.zhao.giftsaydemo.category.strategy.subject;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.util.VolleySingle;

/**
 * Created by 华哥哥 on 16/5/21.
 */
@BindContent(R.layout.activity_strategy_channels)

public class SubjectChannelsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.aty_strategy_channels_lv)
    private ListView listView;
    private SubjectChannelsAdapter adapter;
    @Override
    public void initData() {

        setTitle();

        int id = getIntent().getIntExtra("Id", 0);
        VolleySingle.addRequest("http://api.liwushuo.com/v2/collections/" + id + "/posts?limit=20&offset=0", SubjectChannelsBean.class, new Response.Listener<SubjectChannelsBean>() {
            @Override
            public void onResponse(SubjectChannelsBean response) {
                adapter.setData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        adapter = new SubjectChannelsAdapter(this);
        listView.setAdapter(adapter);

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
}
