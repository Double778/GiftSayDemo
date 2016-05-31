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
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDaoTool;
import com.zhao.giftsaydemo.home.channels.DetailsAdapter;
import com.zhao.giftsaydemo.util.VolleySingle;

/**
 * Created by 华哥哥 on 16/5/21.
 * 专题频道页面
 */
@BindContent(R.layout.activity_strategy_channels)

public class SubjectChannelsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.aty_strategy_channels_lv)
    private ListView listView;
    //private SubjectChannelsAdapter adapter;
    private DetailsAdapter adapter;
    private StrategyDaoTool strategyDaoTool;
    @Override
    public void initData() {
        strategyDaoTool = new StrategyDaoTool();

        setTitle();

        // 根据Id频道网址获取各频道的数据
        final int id = getIntent().getIntExtra("Id", 0);
        VolleySingle.addRequest("http://api.liwushuo.com/v2/collections/" + id + "/posts?limit=20&offset=0", SubjectChannelsBean.class, new Response.Listener<SubjectChannelsBean>() {
            @Override
            public void onResponse(SubjectChannelsBean response) {
                for (int i = 0; i < response.getData().getPosts().size(); i++) {
                    Strategy strategy = new Strategy(
                            System.currentTimeMillis(),
                            id,
                            response.getData().getPosts().get(i).getTitle(),
                            response.getData().getPosts().get(i).getUrl(),
                            response.getData().getPosts().get(i).getCover_image_url(),
                            response.getData().getPosts().get(i).isLiked(),
                            response.getData().getPosts().get(i).getLikes_count());
                    // 获取完添加进数据库
                    strategyDaoTool.addStrategy(strategy);
                }
                adapter.setChannels(id);
                // 根据channels从数据库获取数据
                adapter.setStrategies(strategyDaoTool.queryStrategyByChannels(id));
                //adapter.setData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        adapter = new DetailsAdapter(this);
        //adapter = new SubjectChannelsAdapter(this);
        listView.setAdapter(adapter);

    }

    // 设置标题
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
