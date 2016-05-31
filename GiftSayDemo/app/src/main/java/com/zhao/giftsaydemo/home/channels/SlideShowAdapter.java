package com.zhao.giftsaydemo.home.channels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.category.strategy.subject.SubjectChannelsActivity;
import com.zhao.giftsaydemo.home.bean.SlideShowBean;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/20.
 * 轮播图adapter
 */
public class SlideShowAdapter extends PagerAdapter {
    private List<String> imageUrls;
    private Context context;
    private List<Integer> ids;
    private SlideShowBean slideShowBean;

    public void setSlideShowBean(SlideShowBean slideShowBean) {
        this.slideShowBean = slideShowBean;
        notifyDataSetChanged();
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
        notifyDataSetChanged();
    }

    public SlideShowAdapter(Context context) {
        this.context = context;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageUrls == null ? 0 : imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slide_show_view, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_slide_show_view_iv);
        Picasso.with(context).load(imageUrls.get(position)).config(Bitmap.Config.RGB_565).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SubjectChannelsActivity.class);
                intent.putExtra("Id", ids.get(position));
                if (slideShowBean.getData().getBanners().get(position).getTarget() != null) {
                    intent.putExtra("name", slideShowBean.getData().getBanners().get(position).getTarget().getTitle());
                }
                context.startActivity(intent);
            }

        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
