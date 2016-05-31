package com.zhao.giftsaydemo.category.gift.details;

import android.view.KeyEvent;
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

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
