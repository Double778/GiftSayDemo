package com.zhao.giftsaydemo.home.channels;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyDetailsActivity;
import com.zhao.giftsaydemo.db.GreenDaoSingle;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDao;
import com.zhao.giftsaydemo.home.bean.HomeChannelsBean;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/10.
 */
public class DetailsAdapter extends BaseAdapter {
    private Context context;
    private HomeChannelsBean data;
    private StrategyDao strategyDao;


    public void setData(HomeChannelsBean data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public DetailsAdapter(Context context) {
        this.context = context;
        strategyDao = GreenDaoSingle.getInstance().getStrategyDao();
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
        holder.numTv.setText(String.valueOf(data.getData().getItems().get(position).getLikes_count()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = data.getData().getItems().get(position).getUrl();
                Intent intent = new Intent(context, StrategyDetailsActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getData().getItems().get(position).isLiked()) {
                    data.getData().getItems().get(position).setLiked(false);
                    holder.checkBox.setChecked(false);
                    Log.d("DetailsAdapter", "--" + data.getData().getItems().get(position).isLiked());
                    Log.d("DetailsAdapter", "--" + holder.checkBox.isChecked());
                    // TODO 从数据库删除


                } else {
                    data.getData().getItems().get(position).setLiked(true);
                    holder.checkBox.setChecked(true);
                    Log.d("DetailsAdapter", "---" + data.getData().getItems().get(position).isLiked());
                    Log.d("DetailsAdapter", "---" + holder.checkBox.isChecked());
                    //  加入数据库
                    Strategy strategy = new Strategy((long) position, data.getData().getItems().get(position).getTitle(),
                            data.getData().getItems().get(position).getUrl(),
                            data.getData().getItems().get(position).getCover_image_url());
                    strategyDao.insert(strategy);

                }
            }
        });
        if (data.getData().getItems().get(position).isLiked()) {
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
}
