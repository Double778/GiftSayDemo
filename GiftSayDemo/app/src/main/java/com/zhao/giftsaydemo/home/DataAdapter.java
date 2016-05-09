package com.zhao.giftsaydemo.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;

import java.util.List;

/**
 * Created by 华哥哥 on 16/5/9.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder>{
    private Context context;
    List<String> strings;

    public void setStrings(List<String> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    public DataAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings == null? 0: strings.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_home_show_tv);
        }
    }
}
