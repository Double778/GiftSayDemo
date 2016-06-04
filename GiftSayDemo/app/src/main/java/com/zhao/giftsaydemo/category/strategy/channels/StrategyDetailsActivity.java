package com.zhao.giftsaydemo.category.strategy.channels;

import android.content.Intent;
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
import com.zhao.giftsaydemo.db.GreenDaoTool;
import com.zhao.giftsaydemo.value.GiftSayValues;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by 华哥哥 on 16/5/18.
 * 攻略详情页面
 */
@BindContent(R.layout.activity_strategy_details)
public class StrategyDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.activity_strategy_details_wv)
    private WebView webView;
    private CheckBox checkBox;
    private GreenDaoTool greenDaoTool;
    private String url;
    private ImageView shareIv;
    private Platform platform;
    private Strategy strategy;


    @Override
    public void initData() {
        ShareSDK.initSDK(this);
        platform = ShareSDK.getPlatform(this, QQ.NAME);


        greenDaoTool = new GreenDaoTool();
        // 设置标题
        setTitle();
        url = getIntent().getStringExtra(GiftSayValues.INTENT_STRATEGY_DETAILS_URL);
        strategy = greenDaoTool.queryStrategyByUrl(url);
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


        if (!(getIntent().getIntExtra(GiftSayValues.INTENT_SUBJECT_CHANNELS_TAG, 0) == GiftSayValues.FROM_SUBJECT_CHANNELS_ACTIVITY)) {
            setLiked();
        }


    }


    // 设置详情页收藏按钮状态

    private void setLiked() {
        final Strategy strategy = greenDaoTool.queryStrategyByUrl(url);
        if (strategy.getIsLiked()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        // 收藏按钮几点事件
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (platform.getDb().isValid()) {

                    if (strategy.getIsLiked()) {

                        strategy.setIsLiked(false);
                        strategy.setLikeCount(strategy.getLikeCount() - 1);
                        greenDaoTool.update(strategy);
                        checkBox.setChecked(false);
                        Toast.makeText(StrategyDetailsActivity.this, "取消喜欢", Toast.LENGTH_SHORT).show();

                    } else {

                        strategy.setIsLiked(true);
                        strategy.setLikeCount(strategy.getLikeCount() + 1);
                        greenDaoTool.update(strategy);
                        checkBox.setChecked(true);
                        Toast.makeText(StrategyDetailsActivity.this, "喜欢成功", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(StrategyDetailsActivity.this, "要先登录哦", Toast.LENGTH_SHORT).show();
                }
//                // 发送收藏状态改变了的广播
                Intent intent = new Intent(GiftSayValues.LIKE_CHANGED_RECEIVER);
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
        shareIv = (ImageView) findViewById(R.id.title_share_iv);
        shareIv.setVisibility(View.VISIBLE);
        shareIv.setOnClickListener(this);

    }

    // 返回键
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_iv:
                finish();
                break;
            case R.id.title_share_iv:
                showShare();
                break;
        }
    }


    // web视图
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        //       oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(url);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(strategy.getName());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        oks.setTitle(strategy.getName());
        oks.setTitleUrl(url);
        oks.setText(strategy.getName());
        oks.setImageUrl(strategy.getImgUrl());

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        platform = ShareSDK.getPlatform(this, QQ.NAME);

    }
}
