package com.zhao.giftsaydemo.util;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zhao.giftsaydemo.base.MyApplication;

/**
 * Created by 华哥哥 on 16/5/12.
 * 单例的请求队列
 */
public class MyRequestQueue {
    private static RequestQueue myRequestQueue;

    public static RequestQueue getInstance() {
        if (myRequestQueue == null) {

            synchronized (MyRequestQueue.class) {

                if (myRequestQueue == null) {
                    myRequestQueue = Volley.newRequestQueue(MyApplication.context);
                }
            }
        }
        return myRequestQueue;
    }

    private MyRequestQueue() {
    }
}
