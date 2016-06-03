package com.zhao.giftsaydemo.home.channels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyDetailsActivity;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.GreenDaoTool;

import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/10.
 * 主页各Fragment中ListView的适配器
 */
public class ChannelsAdapter extends BaseAdapter {
    private Context context;
    private GreenDaoTool greenDaoTool;
    private List<Strategy> strategies;
    private int channels;

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public void setStrategies(List<Strategy> strategies) {
        this.strategies = strategies;
        notifyDataSetChanged();
    }


    public ChannelsAdapter(Context context) {
        this.context = context;
        greenDaoTool = new GreenDaoTool();
        ShareSDK.initSDK(context);


    }

    @Override
    public int getCount() {
        return strategies == null ? 0 : strategies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    ViewHolder holder = null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(strategies.get(position).getName());
        Picasso.with(context).load(strategies.get(position).getImgUrl()).config(Bitmap.Config.RGB_565).into(holder.backgroundIv);
        holder.numTv.setText(String.valueOf(strategies.get(position).getLikeCount()));
        // ListView行布局点击事件
        holder.layout.setOnClickListener(new View.OnClickListener() {
            // 跳转攻略详情界面
            @Override
            public void onClick(View v) {
                String url = strategies.get(position).getUrl();
                Intent intent = new Intent(context, StrategyDetailsActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);

            }
        });
        // 收藏按钮点击事件
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform platform = ShareSDK.getPlatform(context, QQ.NAME);
                if (platform.getDb().isValid()) {

                    // 根据pos查询出点击的那条数据
                    if (strategies.get(position).getIsLiked()) {

                        Strategy strategy = greenDaoTool.queryStrategyByChannels(channels).get(position);
                        // 改变收藏状态
                        strategy.setIsLiked(false);
                        strategy.setLikeCount(strategy.getLikeCount() - 1);
                        // 加回数据库
                        greenDaoTool.update(strategy);


                        holder.checkBox.setChecked(false);

                        notifyDataSetChanged();

                    } else {

                        Strategy strategy = greenDaoTool.queryStrategyByChannels(channels).get(position);
                        strategy.setIsLiked(true);
                        strategy.setLikeCount(strategy.getLikeCount() + 1);

                        greenDaoTool.update(strategy);
                        holder.checkBox.setChecked(true);

                        notifyDataSetChanged();

                    }
                } else {
                    Toast.makeText(context, "要先登录哦", Toast.LENGTH_SHORT).show();

                }
                // 根据是否收藏判断事件内容

            }
        });

        // 设置checkBox状态, 防止复用导致错乱
        if (strategies.get(position).getIsLiked()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }


        return convertView;
    }

    class ViewHolder {
        ImageView backgroundIv;
        TextView textView, numTv;
        RelativeLayout layout;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            checkBox = (CheckBox) itemView.findViewById(R.id.item_home_like_count_cb);
            textView = (TextView) itemView.findViewById(R.id.item_home_content_tv);
            backgroundIv = (ImageView) itemView.findViewById(R.id.item_home_background_iv);
            layout = (RelativeLayout) itemView.findViewById(R.id.item_home_onclick);
            numTv = (TextView) itemView.findViewById(R.id.item_home_like_count_num_tv);
        }
    }

}
