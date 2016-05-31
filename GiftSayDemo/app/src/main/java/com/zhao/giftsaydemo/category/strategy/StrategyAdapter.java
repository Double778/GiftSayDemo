package com.zhao.giftsaydemo.category.strategy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyChannelsActivity;
import com.zhao.giftsaydemo.util.MyGridView;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/14.
 * 攻略界面ListViewAdapter
 */
public class StrategyAdapter extends BaseAdapter {
    private Context context;
    private StrategyBean bean;
    private List<Integer> ids;

    public void setBean(StrategyBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public StrategyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean == null ? 0 : bean.getData().getChannel_groups().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_category_strategy_fragment, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(bean.getData().getChannel_groups().get(position).getName());

        // ListView 嵌套GridView
        viewHolder.gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return bean == null ? 0 : bean.getData().getChannel_groups().get(position).getChannels().size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int pos, View convertView, ViewGroup parent) {

                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_category_gv, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.textView.setText(bean.getData().getChannel_groups().get(position).getChannels().get(pos).getName());
                Picasso.with(context).load(bean.getData().getChannel_groups().get(position).getChannels().get(pos).getIcon_url()).config(Bitmap.Config.RGB_565).into(holder.imageView);
                ids = new ArrayList<>();
                for (int i = 0; i < bean.getData().getChannel_groups().get(position).getChannels().size(); i++) {
                    ids.add(bean.getData().getChannel_groups().get(position).getChannels().get(pos).getId());
                }

                return convertView;
            }

            // GridView的ViewHolder
            class ViewHolder {
                ImageView imageView;
                TextView textView;

                public ViewHolder(View itemView) {
                    imageView = (ImageView) itemView.findViewById(R.id.item_fragment_category_gv_iv);
                    textView = (TextView) itemView.findViewById(R.id.item_fragment_category_gv_tv);
                }
            }
        });

        // GridViewItem的点击事件
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int gridViewPos, long id) {
                // 跳转攻略频道页面
                Intent intent = new Intent(context, StrategyChannelsActivity.class);
                intent.putExtra("Id", bean.getData().getChannel_groups().get(position).getChannels().get(gridViewPos).getId());
                intent.putExtra("name", bean.getData().getChannel_groups().get(position).getChannels().get(gridViewPos).getName());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    // ListViewViewHolder
    class ViewHolder {

        TextView textView;
        MyGridView gridView;

        public ViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.item_fragment_category_strategy_fragment_name_tv);
            gridView = (MyGridView) itemView.findViewById(R.id.item_fragment_category_strategy_fragment_gv);
        }

    }


}
