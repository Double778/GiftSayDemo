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
import com.zhao.giftsaydemo.db.GreenDaoTool;
import com.zhao.giftsaydemo.home.channels.ChannelsAdapter;
import com.zhao.giftsaydemo.util.VolleySingle;

/**
 * Created by 华哥哥 on 16/5/17.
 * 攻略频道页面
 */
@BindContent(R.layout.activity_strategy_channels)
public class StrategyChannelsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.aty_strategy_channels_lv)
    private ListView listView;
   // private StrategyChannelsAdapter adapter;
    private ChannelsAdapter adapter;
    private GreenDaoTool greenDaoTool;
    @Override
    public void initData() {
        greenDaoTool = new GreenDaoTool();

        // 设置标题
        setTitle();
        final int id = getIntent().getIntExtra("Id", 0);
        // 获取数据
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
                    // 加入数据库
                    greenDaoTool.addStrategy(strategy);
                }
                adapter.setChannels(id);
                // 根据频道从数据库获取数据
                adapter.setStrategies(greenDaoTool.queryStrategyByChannels(id));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //adapter = new StrategyChannelsAdapter(this);
        adapter = new ChannelsAdapter(this);
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

    // 返回键
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_iv:
                finish();
                break;
        }
    }
}
