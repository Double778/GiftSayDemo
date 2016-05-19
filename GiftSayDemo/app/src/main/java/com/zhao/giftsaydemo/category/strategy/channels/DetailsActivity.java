package com.zhao.giftsaydemo.category.strategy.channels;

import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;

/**
 * Created by 华哥哥 on 16/5/18.
 */
@BindContent(R.layout.activity_strategy_details)
public class DetailsActivity extends BaseActivity{
    @BindView(R.id.activity_strategy_details_wv)
    private WebView webView;

    @Override
    public void initData() {
        String url = getIntent().getStringExtra("url");
        Log.d("DetailsActivity", url);
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webView.setWebViewClient(new WebViewClient());

    }
}
