package com.zhao.giftsaydemo.category.strategy.subject;

import android.util.Log;
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
import com.zhao.giftsaydemo.category.strategy.channels.HeadViewBean;
import com.zhao.giftsaydemo.util.VolleySingle;

/**
 * Created by 华哥哥 on 16/5/19.
 */
@BindContent(R.layout.activity_strategy_channels)
public class AllSubjectActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.aty_strategy_channels_lv)
    private ListView listView;
    private AllSubjectAdapter adapter;

    @Override
    public void initData() {

        setTitle();

        VolleySingle.addRequest("http://api.liwushuo.com/v2/collections?limit=20&offset=0", HeadViewBean.class, new Response.Listener<HeadViewBean>() {
            @Override
            public void onResponse(HeadViewBean response) {
                adapter.setBean(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        adapter = new AllSubjectAdapter(this);
        listView.setAdapter(adapter);

    }


    private void setTitle() {
        findViewById(R.id.title_right_iv).setVisibility(View.INVISIBLE);
        ImageView imageView = (ImageView) findViewById(R.id.title_left_iv);
        imageView.setImageResource(R.mipmap.ic_action_back);
        imageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_iv:
                finish();
                break;
        }
    }
}
