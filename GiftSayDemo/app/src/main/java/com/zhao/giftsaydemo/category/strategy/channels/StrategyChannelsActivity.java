package com.zhao.giftsaydemo.category.strategy.channels;

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
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDaoTool;
import com.zhao.giftsaydemo.pop.PopBean;
import com.zhao.giftsaydemo.util.VolleySingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华哥哥 on 16/5/17.
 */
@BindContent(R.layout.activity_strategy_channels)
public class StrategyChannelsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.aty_strategy_channels_lv)
    private ListView listView;
    private StrategyChannelsAdapter adapter;
    private StrategyDaoTool strategyDaoTool;
    @Override
    public void initData() {
        strategyDaoTool = new StrategyDaoTool();

        setTitle();
        final int id = getIntent().getIntExtra("Id", 0);
        VolleySingle.addRequest("http://api.liwushuo.com/v2/channels/" + id + "/items?limit=20&offset=0", StrategyChannelsBean.class, new Response.Listener<StrategyChannelsBean>() {
            @Override
            public void onResponse(StrategyChannelsBean response) {
                for (int i = 0; i < response.getData().getItems().size(); i++) {
                    Strategy strategy = new Strategy(
                            System.currentTimeMillis(),
                            id,
                            response.getData().getItems().get(i).getTitle(),
                            response.getData().getItems().get(i).getUrl(),
                            response.getData().getItems().get(i).getCover_image_url(),
                            response.getData().getItems().get(i).isLiked(),
                            response.getData().getItems().get(i).getLikes_count());
                    strategyDaoTool.addStrategy(strategy);
                }
                adapter.setChannels(id);
                adapter.setStrategies(strategyDaoTool.queryStrategyByChannels(id));

//                adapter.setData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        adapter = new StrategyChannelsAdapter(this);
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
