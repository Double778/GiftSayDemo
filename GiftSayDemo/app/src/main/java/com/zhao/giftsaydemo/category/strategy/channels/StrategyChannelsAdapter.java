package com.zhao.giftsaydemo.category.strategy.channels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDaoTool;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;


/**
 * Created by 华哥哥 on 16/5/10.
 */
public class StrategyChannelsAdapter extends BaseAdapter {
    private Context context;
    private StrategyDaoTool strategyDaoTool;
    private List<Strategy> strategies;
    private int channels;
    //private LikeChangedReceiver likeChangedReceiver;

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public void setStrategies(List<Strategy> strategies) {
        this.strategies = strategies;
        notifyDataSetChanged();
    }


    public StrategyChannelsAdapter(Context context) {
        this.context = context;
        strategyDaoTool = new StrategyDaoTool();
//        likeChangedReceiver = new LikeChangedReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.zhao.giftsaydemo.LikeChanged");
//        context.registerReceiver(likeChangedReceiver, filter);


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
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = strategies.get(position).getUrl();
                Intent intent = new Intent(context, StrategyDetailsActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strategies.get(position).getIsLiked()) {
                    Strategy strategy = strategyDaoTool.queryStrategyByChannels(channels).get(position);

                    strategy.setIsLiked(false);
                    strategy.setLikeCount(strategy.getLikeCount() - 1);
                    strategyDaoTool.update(strategy);


                    holder.checkBox.setChecked(false);

                    notifyDataSetChanged();

                } else {

                    Strategy strategy = strategyDaoTool.queryStrategyByChannels(channels).get(position);
                    strategy.setIsLiked(true);
                    strategy.setLikeCount(strategy.getLikeCount() + 1);

                    strategyDaoTool.update(strategy);
                    holder.checkBox.setChecked(true);

                    notifyDataSetChanged();

                }
            }
        });
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

//    class LikeChangedReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            strategies = strategyDaoTool.queryStrategyByChannels(channels);
//            notifyDataSetChanged();
//        }
//    }


}
//
///**
// * Created by 华哥哥 on 16/5/10.
// */
//public class StrategyChannelsAdapter extends BaseAdapter {
//    private Context context;
//    private StrategyChannelsBean data;
//
//
//    public void setData(StrategyChannelsBean data) {
//        this.data = data;
//        notifyDataSetChanged();
//    }
//
//    public StrategyChannelsAdapter(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return data == null ? 0 : data.getData().getItems().size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    ViewHolder holder = null;
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.numTv.setText(String.valueOf(data.getData().getItems().get(position).getLikes_count()));
//        holder.textView.setText(data.getData().getItems().get(position).getTitle());
//        Picasso.with(context).load(data.getData().getItems().get(position).getCover_image_url()).into(holder.backgroundIv);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = data.getData().getItems().get(position).getUrl();
//                Intent intent = new Intent(context, StrategyDetailsActivity.class);
//                intent.putExtra("url", url);
//                context.startActivity(intent);
//            }
//        });
//
//        return convertView;
//    }
//
//    class ViewHolder {
//        ImageView backgroundIv;
//        TextView textView, numTv;
//        CardView cardView;
//
//        public ViewHolder(View itemView) {
//            cardView = (CardView) itemView.findViewById(R.id.item_channels_cv);
//            textView = (TextView) itemView.findViewById(R.id.item_home_content_tv);
//            backgroundIv = (ImageView) itemView.findViewById(R.id.item_home_background_iv);
//            numTv = (TextView) itemView.findViewById(R.id.item_home_like_count_num_tv);
//        }
//    }
//}

