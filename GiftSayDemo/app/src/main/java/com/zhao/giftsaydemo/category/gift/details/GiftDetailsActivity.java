package com.zhao.giftsaydemo.category.gift.details;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.category.gift.channels.GiftChannelsBean;
import com.zhao.giftsaydemo.db.Gift;
import com.zhao.giftsaydemo.db.GreenDaoTool;


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
    private GiftDetailsHeadAdapter giftDetailsHeadAdapter;
    private GiftChannelsBean.DataBean.ItemsBean data;
    private TextView nameTv;
    @BindView(R.id.gift_details_like_cb)
    private CheckBox checkBox;
    private GreenDaoTool greenDaoTool;


    @Override
    public void initData() {

        greenDaoTool = new GreenDaoTool();
        data = getIntent().getParcelableExtra("data");

        if (greenDaoTool.hasThisGift(data.getPurchase_url())) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        nameTv = (TextView) findViewById(R.id.gift_details_name_tv);
        nameTv.setText(data.getName());
        ((TextView) findViewById(R.id.gift_details_price_tv)).setText(data.getPrice());
        ((TextView) findViewById(R.id.gift_details_description_tv)).setText(data.getDescription());

        giftDetailsHeadAdapter = new GiftDetailsHeadAdapter(this);
        giftDetailsHeadAdapter.setImageUrls(data.getImage_urls());
        headViewPager.setAdapter(giftDetailsHeadAdapter);

        // WebView显示url
        webView.loadUrl(data.getUrl());
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
                Toast.makeText(GiftDetailsActivity.this, "hahah", Toast.LENGTH_SHORT).show();
                if (greenDaoTool.hasThisGift(data.getPurchase_url())) {
                    checkBox.setChecked(false);
                    greenDaoTool.delGiftByTaobaoUrl(data.getPurchase_url());
                } else {
                    greenDaoTool.addGift(new Gift(System.currentTimeMillis(), data.getCover_image_url(), data.getName(), data.getPurchase_url()));
                    checkBox.setChecked(true);
                }
                break;
            case R.id.gift_details_bug_btn:
                Intent intent = new Intent(this, TaoBaoWebActivity.class);
                intent.putExtra("buy", data.getPurchase_url());
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
