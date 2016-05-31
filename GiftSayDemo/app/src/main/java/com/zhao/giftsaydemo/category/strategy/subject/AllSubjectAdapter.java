package com.zhao.giftsaydemo.category.strategy.subject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/19.
 * 全部专题页面ListView设配器
 */
public class AllSubjectAdapter extends BaseAdapter{
    private Context context;
    private HeadViewBean bean;

    public void setBean(HeadViewBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public AllSubjectAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean == null? 0: bean.getData().getCollections().size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_all_subject, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(bean.getData().getCollections().get(position).getCover_image_url()).config(Bitmap.Config.RGB_565).into(holder.imageView);
        holder.titleTv.setText(bean.getData().getCollections().get(position).getTitle());
        holder.subTitleTv.setText(bean.getData().getCollections().get(position).getSubtitle());
        holder.titleTv.setTextSize(16);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubjectChannelsActivity.class);
                intent.putExtra("Id", bean.getData().getCollections().get(position).getId());
                intent.putExtra("name", bean.getData().getCollections().get(position).getTitle());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView titleTv, subTitleTv;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(View itemView) {
            cardView = (CardView) itemView.findViewById(R.id.item_all_subject_cv);
            titleTv = (TextView) itemView.findViewById(R.id.item_all_subject_title);
            subTitleTv = (TextView) itemView.findViewById(R.id.item_all_subject_subtitle);
            imageView = (ImageView) itemView.findViewById(R.id.item_all_subject_background_iv);
        }
    }

}
