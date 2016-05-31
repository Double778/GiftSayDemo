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
import android.widget.Toast;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.db.Strategy;
import com.zhao.giftsaydemo.db.StrategyDaoTool;


/**
 * Created by 华哥哥 on 16/5/18.
 * 攻略详情页面
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
        // 设置标题
        setTitle();
        url = getIntent().getStringExtra("url");
        // WebView 显示url
        webView.loadUrl(url);
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


        if (!(getIntent().getIntExtra("tag", 0) == 1)) {
            setLiked();
        }


    }


    // 设置详情页收藏按钮状态

    private void setLiked() {
        final Strategy strategy = strategyDaoTool.queryStrategyByUrl(url);
        if (strategy.getIsLiked()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        // 收藏按钮几点事件
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strategy.getIsLiked()) {

                    strategy.setIsLiked(false);
                    strategy.setLikeCount(strategy.getLikeCount() - 1);
                    strategyDaoTool.update(strategy);


                    checkBox.setChecked(false);
                    Toast.makeText(StrategyDetailsActivity.this, "取消喜欢", Toast.LENGTH_SHORT).show();
                } else {
                    strategy.setIsLiked(true);
                    strategy.setLikeCount(strategy.getLikeCount() + 1);

                    strategyDaoTool.update(strategy);


                    checkBox.setChecked(true);
                    Toast.makeText(StrategyDetailsActivity.this, "喜欢成功", Toast.LENGTH_SHORT).show();

                }
                // 发送收藏状态改变了的广播
                Intent intent = new Intent("com.zhao.giftsaydemo.LikeChanged");

                sendBroadcast(intent);
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

    // 返回键
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
