package com.zhao.giftsaydemo.pop.details;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.category.gift.details.GiftDetailsHeadAdapter;
import com.zhao.giftsaydemo.category.gift.details.TaoBaoWebActivity;
import com.zhao.giftsaydemo.pop.PopBean;

/**
 * Created by 华哥哥 on 16/5/21.
 * 礼物详情页面
 */
@BindContent(R.layout.activity_gift_details)
public class PopDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gift_details_coll_toolbar_vp)
    private ViewPager headViewPager;
    @BindView(R.id.gift_details_bug_btn)
    private Button button;
//    @BindView(R.id.gift_details_vp)
//    private ViewPager contentViewPager;
//    @BindView(R.id.gift_details_tab)
//    private TabLayout tabLayout;

    @BindView(R.id.web)
    private WebView webView;
    private GiftDetailsHeadAdapter giftDetailsHeadAdapter;
    // 礼物item数据类
    private PopBean.DataBean.ItemsBean.DataBean1 data;
    private TextView nameTv;

    @Override
    public void initData() {

        // 显示数据
        data = getIntent().getParcelableExtra("data");
        nameTv = (TextView) findViewById(R.id.gift_details_name_tv);
        nameTv.setText(data.getName());
        ((TextView)findViewById(R.id.gift_details_price_tv)).setText(data.getPrice());
        ((TextView)findViewById(R.id.gift_details_description_tv)).setText(data.getDescription());

        // 页面上方显示可滑动图片
        giftDetailsHeadAdapter = new GiftDetailsHeadAdapter(this);
        giftDetailsHeadAdapter.setImageUrls(data.getImage_urls());
        headViewPager.setAdapter(giftDetailsHeadAdapter);

        // webView显示url
        webView.loadUrl(data.getUrl());
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webView.requestFocus();
        webView.setWebViewClient(new MyWebViewClient());
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;            }
        });

        // 跳转淘宝按钮
        button.setOnClickListener(this);


    }

    // 跳转淘宝页面
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TaoBaoWebActivity.class);
        intent.putExtra("buy", data.getPurchase_url());
        startActivity(intent);
    }

    private class  MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("MyWebViewClient", url);
            view.loadUrl(url);
            return true;
        }

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
//
//            webView.goBack();
//            return true;
//
//        }
//        return false;
//    }
}
