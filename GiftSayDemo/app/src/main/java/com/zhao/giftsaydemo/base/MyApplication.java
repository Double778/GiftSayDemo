package com.zhao.giftsaydemo.base;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 华哥哥 on 16/5/10.
 * 自定义Application
 */
public class MyApplication extends Application{
    public static Context context;

    // 第一个生命周期中我们对Context赋值
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        // 推送
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    public static Context getContext(){
        return context;
    }
}
