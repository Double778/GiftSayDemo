package com.zhao.giftsaydemo.pop;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/10.
 * 热门页面RecycleView数据类
 */
public class PopAdapter extends RecyclerView.Adapter<PopAdapter.MyViewHolder> {
    private Context context;
    private PopBean bean;
    private OnClickListener onClickListener;
    private View itemView;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setPopBean(PopBean popBean) {
        this.bean = popBean;
        notifyDataSetChanged();
    }

    public PopAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(context).inflate(R.layout.item_pop, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.contentTv.setText(bean.getData().getItems().get(position).getData().getName());
        holder.priceTv.setText(bean.getData().getItems().get(position).getData().getPrice());
        Picasso.with(context).load(bean.getData().getItems().get(position).getData().getCover_image_url()).resize(380, 380).config(Bitmap.Config.RGB_565).into(holder.imageView);
        // 行布局点击事件
        if (onClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onClickListener.onClick(pos, bean);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return bean == null ? 0 : bean.getData().getItems().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView contentTv, priceTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_pop_iv);
            contentTv = (TextView) itemView.findViewById(R.id.item_pop_content_tv);
            priceTv = (TextView) itemView.findViewById(R.id.item_pop_price_tv);
        }
    }

    // 点击事件接口
    public interface OnClickListener {
        void onClick(int pos, PopBean bean);
    }
}
