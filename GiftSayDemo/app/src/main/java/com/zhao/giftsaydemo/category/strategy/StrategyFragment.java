package com.zhao.giftsaydemo.category.strategy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.strategy.subject.HeadViewBean;
import com.zhao.giftsaydemo.category.strategy.subject.AllSubjectActivity;
import com.zhao.giftsaydemo.category.strategy.subject.SubjectChannelsActivity;
import com.zhao.giftsaydemo.util.VolleySingle;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/14.
 * 分类中的攻略页面
 */
@BindContent(R.layout.fragment_category_strategy_fragment)
public class StrategyFragment extends BaseFragment{
    @BindView(R.id.fragment_category_strategy_fragment_lv)
    private ListView listView;
    private StrategyAdapter adapter;

    int[] ids = {R.id.head_view_horizontal_iv_1, R.id.head_view_horizontal_iv_2, R.id.head_view_horizontal_iv_3, R.id.head_view_horizontal_iv_4, R.id.head_view_horizontal_iv_5, R.id.head_view_horizontal_iv_6, R.id.head_view_horizontal_iv_7, R.id.head_view_horizontal_iv_8,R.id.head_view_horizontal_iv_9,R.id.head_view_horizontal_iv_10};

    @Override
    public void initData() {

        // 头布局
        addHeadView();

        // 获取数据
        VolleySingle.addRequest("http://api.liwushuo.com/v2/channel_groups/all", StrategyBean.class, new Response.Listener<StrategyBean>() {
            @Override
            public void onResponse(StrategyBean response) {
                adapter = new StrategyAdapter(context);
                listView.setAdapter(adapter);
                adapter.setBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



    }

    // 添加头布局
    private void addHeadView() {

        // 获取头布局数据
        VolleySingle.addRequest("http://api.liwushuo.com/v2/collections?limit=20&offset=0", HeadViewBean.class, new Response.Listener<HeadViewBean>() {


            @Override
            public void onResponse(final HeadViewBean response) {
                View view = LayoutInflater.from(context).inflate(R.layout.head_view_horizontal_sv, null);
                // 头布局添加数据
                for (int i = 0; i < ids.length; i++) {
                    Picasso.with(context).load(response.getData().getCollections().get(i).getBanner_image_url()).config(Bitmap.Config.RGB_565).into(((ImageView) view.findViewById(ids[i])));
                    final int finalI = i;
                    // 设置点击事件
                    ((ImageView)view.findViewById(ids[i])).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 跳转专题频道界面
                            Intent intent = new Intent(context, SubjectChannelsActivity.class);
                            intent.putExtra("Id", response.getData().getCollections().get(finalI).getId());
                            intent.putExtra("name", response.getData().getCollections().get(finalI).getTitle());
                            context.startActivity(intent);

                        }
                    });
                }
                TextView textView = (TextView) view.findViewById(R.id.head_view_left_tv);
                textView.setVisibility(View.VISIBLE);
                textView.setText("专题");
                TextView showAllTv = (TextView) view.findViewById(R.id.head_view_right_tv);
                showAllTv.setVisibility(View.VISIBLE);
                showAllTv.setText("查看全部");
                // 点击查看全部跳转全部专题页面
                showAllTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AllSubjectActivity.class);
                        context.startActivity(intent);

                    }
                });
                listView.addHeaderView(view);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


}
