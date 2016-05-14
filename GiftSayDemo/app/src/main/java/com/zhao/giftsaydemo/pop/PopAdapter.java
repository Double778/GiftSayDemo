package com.zhao.giftsaydemo.pop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/10.
 */
public class PopAdapter extends RecyclerView.Adapter<PopAdapter.MyViewHolder> {
    private Context context;
    private PopBean popBean;
//    public void setDrawables(List<Integer> drawables) {
//        this.drawables = drawables;
//        notifyDataSetChanged();
//    }


    public void setPopBean(PopBean popBean) {
        this.popBean = popBean;
        notifyDataSetChanged();
    }

    public PopAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pop, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.contentTv.setText(popBean.getData().getItems().get(position).getData().getName());
        holder.priceTv.setText(popBean.getData().getItems().get(position).getData().getPrice());
        Picasso.with(context).load(popBean.getData().getItems().get(position).getData().getCover_image_url()).resize(400,400).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return popBean == null ? 0 : popBean.getData().getItems().size();
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
}
