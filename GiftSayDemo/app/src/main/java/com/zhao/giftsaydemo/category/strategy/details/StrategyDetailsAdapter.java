package com.zhao.giftsaydemo.category.strategy.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;
import it.sephiroth.android.library.picasso.Picasso;


/**
 * Created by 华哥哥 on 16/5/10.
 */
public class StrategyDetailsAdapter extends BaseAdapter {
    private Context context;
    private StrategyDetailsBean data;


    public void setData(StrategyDetailsBean data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public StrategyDetailsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.getData().getItems().size();
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


        holder.textView.setText(data.getData().getItems().get(position).getTitle());
        Picasso.with(context).load(data.getData().getItems().get(position).getCover_image_url()).into(holder.backgroundIv);
        holder.alphaIv.setImageResource(R.mipmap.ic_item_home_alpha);


        return convertView;
    }

    class ViewHolder {
        ImageView backgroundIv, alphaIv;
        TextView textView;

        public ViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.item_home_content_tv);
            backgroundIv = (ImageView) itemView.findViewById(R.id.item_home_background_iv);
            alphaIv = (ImageView) itemView.findViewById(R.id.item_home_alpha_iv);
            alphaIv.setAlpha(100);
        }
    }
}
