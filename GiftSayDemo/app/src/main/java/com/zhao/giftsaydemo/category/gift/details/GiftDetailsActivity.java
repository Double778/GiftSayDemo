package com.zhao.giftsaydemo.category.gift.details;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.category.gift.channels.GiftChannelsBean;
import com.zhao.giftsaydemo.db.Gift;
import com.zhao.giftsaydemo.db.GreenDaoTool;
import com.zhao.giftsaydemo.pop.PopBean;
import com.zhao.giftsaydemo.value.GiftSayValues;


/**
 * Created by 华哥哥 on 16/5/20.
 * 礼物详情Activity
 */
@BindContent(R.layout.activity_gift_details)
public class GiftDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gift_details_coll_toolbar_vp)
    private ViewPager headViewPager;
    @BindView(R.id.gift_details_bug_btn)
    private Button button;
    @BindView(R.id.web)
    private WebView webView;
    @BindView(R.id.gift_details_like_cb)
    private CheckBox checkBox;

    private GiftDetailsHeadAdapter giftDetailsHeadAdapter;
    private TextView nameTv;
    private GreenDaoTool greenDaoTool;
    private GiftChannelsBean.DataBean.ItemsBean data;
    private PopBean.DataBean.ItemsBean.DataBean1 popData;
    private int tag;


    @Override
    public void initData() {

        greenDaoTool = new GreenDaoTool();
        giftDetailsHeadAdapter = new GiftDetailsHeadAdapter(this);

        tag = getIntent().getIntExtra(GiftSayValues.INTENT_GIFT_CHANNELS_TAG, 0);

        if (tag == GiftSayValues.FROM_GIFT_CHANNELS_ACTIVITY) {
            data = getIntent().getParcelableExtra(GiftSayValues.INTENT_GIFT_DETAILS_DATA);
            nameTv = (TextView) findViewById(R.id.gift_details_name_tv);
            nameTv.setText(data.getName());
            ((TextView) findViewById(R.id.gift_details_price_tv)).setText(data.getPrice());
            ((TextView) findViewById(R.id.gift_details_description_tv)).setText(data.getDescription());

            giftDetailsHeadAdapter.setImageUrls(data.getImage_urls());
            headViewPager.setAdapter(giftDetailsHeadAdapter);

            // WebView显示url
            webView.loadUrl(data.getUrl());


            if (greenDaoTool.hasThisGift(data.getPurchase_url())) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        } else {
            popData = getIntent().getParcelableExtra(GiftSayValues.INTENT_GIFT_DETAILS_DATA);
            nameTv = (TextView) findViewById(R.id.gift_details_name_tv);
            nameTv.setText(popData.getName());
            ((TextView) findViewById(R.id.gift_details_price_tv)).setText(popData.getPrice());
            ((TextView) findViewById(R.id.gift_details_description_tv)).setText(popData.getDescription());

            giftDetailsHeadAdapter.setImageUrls(popData.getImage_urls());
            headViewPager.setAdapter(giftDetailsHeadAdapter);

            // WebView显示url
            webView.loadUrl(popData.getUrl());


            if (greenDaoTool.hasThisGift(popData.getPurchase_url())) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webView.requestFocus();
        webView.setWebViewClient(new MyWebViewClient());
        // 点击返回可以在web回退, 不直接退出
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });

        button.setOnClickListener(this);
        checkBox.setOnClickListener(this);

    }

    // 点击跳转到淘宝web
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gift_details_like_cb:
                if (tag == GiftSayValues.FROM_GIFT_CHANNELS_ACTIVITY) {

                    if (greenDaoTool.hasThisGift(data.getPurchase_url())) {
                        checkBox.setChecked(false);
                        greenDaoTool.delGiftByTaobaoUrl(data.getPurchase_url());
                    } else {
                        greenDaoTool.addGift(new Gift(System.currentTimeMillis(), data.getCover_image_url(), data.getName(), data.getPurchase_url()));
                        checkBox.setChecked(true);
                    }
                } else {
                    if (greenDaoTool.hasThisGift(popData.getPurchase_url())) {
                        checkBox.setChecked(false);
                        greenDaoTool.delGiftByTaobaoUrl(popData.getPurchase_url());
                    } else {
                        greenDaoTool.addGift(new Gift(System.currentTimeMillis(), popData.getCover_image_url(), popData.getName(), popData.getPurchase_url()));
                        checkBox.setChecked(true);
                    }
                }
                break;
            case R.id.gift_details_bug_btn:
                Intent intent = new Intent(this, TaoBaoWebActivity.class);
                if (tag == 1) {
                    intent.putExtra(GiftSayValues.INTENT_BUY_URL, data.getPurchase_url());
                } else {
                    intent.putExtra(GiftSayValues.INTENT_BUY_URL, popData.getPurchase_url());

                }
                startActivity(intent);
                break;
        }


    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

}
