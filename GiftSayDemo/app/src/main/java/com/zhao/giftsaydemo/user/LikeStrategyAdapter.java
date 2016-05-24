package com.zhao.giftsaydemo.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.db.GreenDaoSingle;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDao;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/23.
 */
public class LikeStrategyAdapter extends BaseAdapter {
    private Context context;
    private List<Strategy> strategies;

    public void setStrategies(List<Strategy> strategies) {
        this.strategies = strategies;
        notifyDataSetChanged();
    }

    public LikeStrategyAdapter(Context context) {
        this.context = context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_like_strategy, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(strategies.get(position).getName());
        Picasso.with(context).load(strategies.get(position).getImgUrl()).resize(200, 100).into(holder.imageView);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            imageView = (ImageView) itemView.findViewById(R.id.item_user_like_strategy_iv);
            textView = (TextView) itemView.findViewById(R.id.item_user_like_strategy_tv);
        }
    }
}
