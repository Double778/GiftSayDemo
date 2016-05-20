package com.zhao.giftsaydemo.category.gift;

import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.util.VolleySingle;

import za.co.immedia.pinnedheaderlistview.PinnedHeaderListView;

/**
 * Created by 华哥哥 on 16/5/14.
 */
@BindContent(R.layout.fragment_category_gift_fragment)
public class GiftFragment extends BaseFragment {

    @BindView(R.id.fragment_category_gift_fragment_left_lv)
    private ListView leftListView;
    @BindView(R.id.fragment_category_gift_fragment_right_lv)
    private PinnedHeaderListView rightListView;
    private boolean isScroll = true;
    private RightAdapter adapter;
    private LeftAdapter leftAdapter;


    @Override
    public void initData() {
        VolleySingle.addRequest("http://api.liwushuo.com/v2/item_categories/tree", GiftBean.class, new Response.Listener<GiftBean>() {
            @Override
            public void onResponse(GiftBean response) {
                leftAdapter = new LeftAdapter(context, response);
                leftListView.setAdapter(leftAdapter);
                adapter = new RightAdapter(context);
                adapter.setGiftBean(response);
                rightListView.setAdapter(adapter);
                leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        isScroll = false;
                        for (int i = 0; i < leftListView.getChildCount(); i++) {
                            if (i == position) {
                                leftListView.getChildAt(i).setBackgroundColor(Color.WHITE);
                            } else {
                                leftListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            }
                        }
                        int rightSection = 0;
                        for (int i = 0; i < position; i++) {
                            rightSection += adapter.getCountForSection(i) + 1;
                        }
                        rightListView.setSelection(rightSection);
                    }
                });
                rightListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (isScroll) {
//                            for (int i = 0; i < leftListView.getCount(); i++) {
//                                if (i == adapter.getSectionForPosition(firstVisibleItem)) {
//                                    leftListView.smoothScrollToPosition(i);
//                                    //leftListView.getChildAt().setBackgroundColor(Color.rgb(255, 255, 255));
//                                    Log.d("GiftFragment", "i:" + i);
//                                } else {
//                                    if (i < leftListView.getChildCount()) {
//                                        leftListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
//                                    }
//                                }
//                            }
                            //     leftListView.smoothScrollToPosition(adapter.getSectionForPosition(firstVisibleItem));
                            //          leftAdapter.setSelectPos(adapter.getSectionForPosition(firstVisibleItem));
                            for (int i = 0; i < leftListView.getChildCount(); i++) {
                                if (i == adapter.getSectionForPosition(firstVisibleItem)) {
                                    leftListView.getChildAt(i).setBackgroundColor(Color.WHITE);

                                } else {
                                    leftListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                }

                            }

                        } else {
                            isScroll = true;
                        }
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_gift_fragment, menu);
//    }
}
