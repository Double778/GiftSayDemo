package com.zhao.giftsaydemo.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 华哥哥 on 16/5/10.
 */
public class MyApplication extends Application{
    public static Context context;
    // Application创建的原因是因为我们

    // 第一个生命周期中我们对Context赋值
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
