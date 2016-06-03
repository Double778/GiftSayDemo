package com.zhao.giftsaydemo.category.gift.details;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;

/**
 * Created by 华哥哥 on 16/5/25.
 * 显示淘宝页面的Activity
 */
@BindContent(R.layout.activity_strategy_details)
public class TaoBaoWebActivity extends BaseActivity{
    @BindView(R.id.activity_strategy_details_wv)
    private WebView webView;
    @Override
    public void initData() {
        findViewById(R.id.title).setVisibility(View.GONE);

        // WebView显示url
        webView.loadUrl(getIntent().getStringExtra("buy"));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webView.setWebViewClient(new MyWebViewClient());


    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
