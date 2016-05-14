package com.zhao.giftsaydemo.category.gift;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.util.MyRequestQueue;
import com.zhao.giftsaydemo.volley.GsonRequest;

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
    private GiftAdapter adapter;
    private GiftBean giftBean;


    @Override
    public void initData() {
        GsonRequest<GiftBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://api.liwushuo.com/v2/item_categories/tree", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new Response.Listener<GiftBean>() {
            @Override
            public void onResponse(final GiftBean response) {
                giftBean = response;
                //Log.d("GiftFragment", "1" + giftBean.getData().getCategories().get(0).getSubcategories().get(0).getName());

                leftListView.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return response.getData().getCategories().size();
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
                            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_category_gift_fragment_left, parent, false);
                            holder = new ViewHolder(convertView);
                            convertView.setTag(holder);
                        } else {
                            holder = (ViewHolder) convertView.getTag();
                        }

                        holder.textView.setText(response.getData().getCategories().get(position).getName());
                        return convertView;
                    }
                    class ViewHolder {
                        TextView textView;

                        public ViewHolder(View itemView) {
                            textView = (TextView) itemView.findViewById(R.id.item_fragment_category_gift_fragment_left_name_tv);
                        }
                    }

                });
                adapter = new GiftAdapter(context);
                adapter.setGiftBean(giftBean);
                rightListView.setAdapter(adapter);
                leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        isScroll = false;
                        for (int i = 0; i < leftListView.getChildCount(); i++) {
                            if (i == position) {
                                leftListView.getChildAt(i).setBackgroundColor(Color.rgb(255, 255, 255));
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
                            for (int i = 0; i < leftListView.getChildCount(); i++) {
                                if (i == adapter.getSectionForPosition(firstVisibleItem)) {
                                    leftListView.getChildAt(i).setBackgroundColor(Color.rgb(255, 255, 255));
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
        }, GiftBean.class);
        MyRequestQueue.getInstance().add(gsonRequest);




    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_gift_fragment, menu);
    }
}
