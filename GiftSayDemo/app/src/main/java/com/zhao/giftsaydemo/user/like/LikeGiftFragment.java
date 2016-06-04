package com.zhao.giftsaydemo.user.like;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.category.gift.details.TaoBaoWebActivity;
import com.zhao.giftsaydemo.category.strategy.channels.StrategyDetailsActivity;
import com.zhao.giftsaydemo.db.Gift;
import com.zhao.giftsaydemo.db.GreenDaoTool;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.util.MyListView;
import com.zhao.giftsaydemo.value.GiftSayValues;

import java.util.List;


/**
 * Created by 华哥哥 on 16/6/3.
 */
@BindContent(R.layout.fragment_like_gift)
public class LikeGiftFragment extends BaseFragment {


    @BindView(R.id.fragment_user_lv)
    private MyListView listView;
    private LikeAdapter adapter;
    private GreenDaoTool greenDaoTool;
    // 下方ListView无数据时的预显示内容
    @BindView(R.id.hint_tv)
    private TextView textView;

    @Override
    public void initData() {

        greenDaoTool = new GreenDaoTool();
        // 查询出已收藏的攻略
        final List<Gift> gifts = greenDaoTool.queryGift();

        adapter = new LikeAdapter(context);
        adapter.setGifts(gifts);
        adapter.setTag(1);
        listView.setAdapter(adapter);
        listView.setEmptyView(textView);

        // 点击收藏的攻略, 跳转到攻略详情页面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, TaoBaoWebActivity.class);
                intent.putExtra(GiftSayValues.INTENT_BUY_URL, gifts.get(position).getTaobaoUrl());
                context.startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                greenDaoTool.delGiftByTaobaoUrl(gifts.get(position).getTaobaoUrl());
                adapter.setGifts(greenDaoTool.queryGift());
                return true;
            }
        });

    }

}
