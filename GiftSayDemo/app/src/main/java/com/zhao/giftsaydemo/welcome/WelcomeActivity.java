package com.zhao.giftsaydemo.welcome;

import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.main.MainActivity;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by 华哥哥 on 16/5/10.
 * 欢迎页
 */
@BindContent(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity{
    @BindView(R.id.activity_welcome_show_iv)
    private ImageView imageView;
    private CountDownTimer timer;
    @Override
    public void initData() {


        imageView.setImageResource(R.mipmap.ic_welcome);
        // 持续3S
        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                // 跳转主页面
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        timer.start();
    }
}
