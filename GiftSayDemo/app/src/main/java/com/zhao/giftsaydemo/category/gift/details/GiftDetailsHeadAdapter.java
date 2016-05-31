package com.zhao.giftsaydemo.category.gift.details;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhao.giftsaydemo.R;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/20.
 * 礼物详情上方可滑动图片adapter
 */
public class GiftDetailsHeadAdapter extends PagerAdapter{
    private List<String> imageUrls;
    private Context context;

    public GiftDetailsHeadAdapter(Context context) {
        this.context = context;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageUrls == null? 0:imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.page_gift_details_head, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.page_gift_details_head_iv);
        Picasso.with(context).load(imageUrls.get(position)).config(Bitmap.Config.RGB_565).into(imageView);
        container.addView(view);
        Log.d("GiftDetailsHeadAdapter", "imageUrls:" + imageUrls);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
