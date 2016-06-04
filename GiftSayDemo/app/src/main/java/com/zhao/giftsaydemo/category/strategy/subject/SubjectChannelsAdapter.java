package com.zhao.giftsaydemo.category.strategy.subject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyDetailsActivity;
import com.zhao.giftsaydemo.value.GiftSayValues;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/21.
 * 专题频道ListView适配器
 */
public class SubjectChannelsAdapter extends BaseAdapter{
    private Context context;
    private SubjectChannelsBean data;


    public void setData(SubjectChannelsBean data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public SubjectChannelsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.getData().getPosts().size();
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
        holder.numTv.setText(String.valueOf(data.getData().getPosts().get(position).getLikes_count()));
        holder.textView.setText(data.getData().getPosts().get(position).getTitle());
        Picasso.with(context).load(data.getData().getPosts().get(position).getCover_image_url()).into(holder.backgroundIv);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = GiftSayValues.STRATEGY_ALL_SUBJECT_DETAILS_URL_START + data.getData().getPosts().get(position).getId();
                Intent intent = new Intent(context, StrategyDetailsActivity.class);
                intent.putExtra(GiftSayValues.INTENT_STRATEGY_DETAILS_URL, url);
                intent.putExtra(GiftSayValues.INTENT_SUBJECT_CHANNELS_TAG, GiftSayValues.FROM_SUBJECT_CHANNELS_ACTIVITY);
                context.startActivity(intent);

            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView backgroundIv;
        TextView textView, numTv;
        CardView cardView;
        public ViewHolder(View itemView) {
            cardView = (CardView) itemView.findViewById(R.id.item_channels_cv);
            textView = (TextView) itemView.findViewById(R.id.item_home_content_tv);
            backgroundIv = (ImageView) itemView.findViewById(R.id.item_home_background_iv);
            numTv = (TextView) itemView.findViewById(R.id.item_home_like_count_num_tv);
        }
    }
}
