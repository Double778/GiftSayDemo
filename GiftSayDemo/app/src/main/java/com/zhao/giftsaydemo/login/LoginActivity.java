package com.zhao.giftsaydemo.login;


import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by 华哥哥 on 16/5/30.
 */
@BindContent(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @BindView(R.id.activity_login_qq_iv)
    private ImageView imageView;

    private Platform platform;

    @Override
    public void initData() {
        ShareSDK.initSDK(this);
        platform = ShareSDK.getPlatform(QQ.NAME);


        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                platform.SSOSetting(false);
                platform.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                platform.showUser(null);

                platform.authorize();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (platform.isValid()) {

            Toast.makeText(this, "已登录", Toast.LENGTH_SHORT).show();
        }
    }
}
