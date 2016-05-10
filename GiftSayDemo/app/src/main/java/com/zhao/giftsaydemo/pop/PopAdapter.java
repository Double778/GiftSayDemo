package com.zhao.giftsaydemo.pop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhao.giftsaydemo.R;

import java.util.List;

/**
 * Created by 华哥哥 on 16/5/10.
 */
public class PopAdapter extends RecyclerView.Adapter<PopAdapter.MyViewHolder> {
    private Context context;
    List<Integer> drawables;

    public void setDrawables(List<Integer> drawables) {
        this.drawables = drawables;
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageView.setImageResource(drawables.get(position));
    }

    @Override
    public int getItemCount() {
        return drawables == null ? 0 : drawables.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_pop_iv);
        }
    }
}
