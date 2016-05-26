package com.zhao.giftsaydemo.category.strategy.channels;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDaoTool;


/**
 * Created by 华哥哥 on 16/5/18.
 */
@BindContent(R.layout.activity_strategy_details)
public class StrategyDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.activity_strategy_details_wv)
    private WebView webView;
    private CheckBox checkBox;
    private StrategyDaoTool strategyDaoTool;
    private String url;

    @Override
    public void initData() {


        strategyDaoTool = new StrategyDaoTool();
        setTitle();
        url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webView.setWebViewClient(new MyWebViewClient());

        setLiked();


    }

    private void setLiked() {
        final Strategy strategy = strategyDaoTool.queryStrategyByUrl(url);
        if (strategy.getIsLiked()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strategy.getIsLiked()) {

                    strategy.setIsLiked(false);
                    strategy.setLikeCount(strategy.getLikeCount() - 1);
                    strategyDaoTool.update(strategy);
                    checkBox.setChecked(false);

                    Intent intent = new Intent("com.zhao.giftsaydemo.LikeChanged");
                    sendBroadcast(intent);

                } else {

                    strategy.setIsLiked(true);
                    strategy.setLikeCount(strategy.getLikeCount() + 1);
                    strategyDaoTool.update(strategy);
                    checkBox.setChecked(true);

                    Intent intent = new Intent("com.zhao.giftsaydemo.LikeChanged");
                    sendBroadcast(intent);

                }
            }
        });

    }

    private void setTitle() {
        findViewById(R.id.title_name_iv).setVisibility(View.INVISIBLE);
        findViewById(R.id.title_right_iv).setVisibility(View.INVISIBLE);
        ImageView imageView = (ImageView) findViewById(R.id.title_left_iv);
        imageView.setImageResource(R.mipmap.ic_action_back);
        imageView.setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.title_name_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setTextSize(16);
        textView.setText("攻略详情");
        checkBox = (CheckBox) findViewById(R.id.title_right_cb);
        checkBox.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_iv:

                finish();
                break;

        }
    }


    // 设置回退
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
//
//            webView.goBack();
//            return true;
//        }
//        return false;
//
//    }

    // web视图
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
