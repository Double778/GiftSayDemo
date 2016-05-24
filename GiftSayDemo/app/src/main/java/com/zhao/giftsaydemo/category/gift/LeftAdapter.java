package com.zhao.giftsaydemo.category.gift;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.util.GetScreenHeightUtil;

/**
 * Created by ChenFengYao on 16/5/17.
 */
public class LeftAdapter extends BaseAdapter{
    int selectPos = 0;
    private Context context;
    private GiftBean response;

    public LeftAdapter(Context context) {
        this.context = context;
    }

    public void setResponse(GiftBean response) {
        this.response = response;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return response == null ? 0 : response.getData().getCategories().size();
    }

    public void setSelectPos(int pos){
        this.selectPos = pos;
        notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_category_gift_fragment_left_lv, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        if (position==selectPos){
//            holder.textView.setTextColor(Color.RED);
//        }else {
//            holder.textView.setTextColor(Color.YELLOW);
//        }
        holder.textView.setText(response.getData().getCategories().get(position).getName());

        return convertView;
    }

    class ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.item_fragment_category_gift_fragment_left_name_tv);
        }
    }


}
