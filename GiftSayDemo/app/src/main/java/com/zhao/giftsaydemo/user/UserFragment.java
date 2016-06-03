package com.zhao.giftsaydemo.user;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;

import com.zhao.giftsaydemo.login.LoginActivity;
import com.zhao.giftsaydemo.user.like.LikeGiftFragment;
import com.zhao.giftsaydemo.user.like.LikeStrategyFragment;


import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 华哥哥 on 16/5/9.
 * 用户页面
 */
@BindContent(R.layout.fragment_user)
public class UserFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.fragment_user_like_gift)
    private RadioButton likeGiftBtn;
    @BindView(R.id.fragment_user_like_strategy)
    private RadioButton likeStrategyBtn;

    @BindView(R.id.fragment_user_user_name_tv)
    private TextView nameTv;
    @BindView(R.id.fragment_user_user_iv)
    private ImageView iconImg;
    private Platform platform;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            nameTv.setText(((String[]) msg.obj)[0]);
            Picasso.with(context).load(((String[]) msg.obj)[1]).resize(100, 100).into(iconImg);
            return false;
        }
    });

    @Override
    public void initData() {
        ShareSDK.initSDK(context);
        iconImg.setOnClickListener(this);
        likeGiftBtn.setOnClickListener(this);
        likeStrategyBtn.setOnClickListener(this);


        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_user_replace, new LikeGiftFragment());
        transaction.commit();


    }


    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.fragment_user_user_iv:
                startActivity(new Intent(context, LoginActivity.class));
                break;
            case R.id.fragment_user_like_gift:
                transaction.replace(R.id.fragment_user_replace, new LikeGiftFragment());
                break;
            case R.id.fragment_user_like_strategy:
                transaction.replace(R.id.fragment_user_replace, new LikeStrategyFragment());

                break;

        }
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 从第三方登录获取授权!
         * 获取授权回调到本地的资源
         */
        platform = ShareSDK.getPlatform(context, QQ.NAME);
        if (platform.getDb().isValid()) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String[] data = {platform.getDb().getUserName(), platform.getDb().getUserIcon()};
                    Message message = new Message();
                    message.obj = data;

                    handler.sendMessage(message);
                }
            }).start();

        }

    }
}
