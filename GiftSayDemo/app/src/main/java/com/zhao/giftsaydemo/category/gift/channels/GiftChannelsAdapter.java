package com.zhao.giftsaydemo.category.gift.channels;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/19.
 * 礼物各频道RecycleView的Adapter
 */
public class GiftChannelsAdapter extends RecyclerView.Adapter<GiftChannelsAdapter.MyViewHolder> {
    private Context context;
    private GiftChannelsBean bean;
    private View itemView;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setBean(GiftChannelsBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public GiftChannelsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(context).inflate(R.layout.item_pop, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.contentTv.setText(bean.getData().getItems().get(position).getName());
        holder.priceTv.setText(bean.getData().getItems().get(position).getPrice());
        Picasso.with(context).load(bean.getData().getItems().get(position).getCover_image_url()).resize(400, 400).config(Bitmap.Config.RGB_565).into(holder.imageView);
        if (onClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onClickListener.onClick(bean, pos);


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

    // RecycleViewItem点击接口
    public interface OnClickListener {
        void onClick(GiftChannelsBean bean, int pos);
    }
}
