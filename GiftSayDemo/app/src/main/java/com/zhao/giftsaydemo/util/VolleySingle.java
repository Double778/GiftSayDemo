package com.zhao.giftsaydemo.util;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zhao.giftsaydemo.base.MyApplication;


/**
 * Created by 华哥哥 on 16/5/16.
 * Volley请求封装
 */
public class VolleySingle {
    private RequestQueue queue;// 请求队列
    private static VolleySingle ourInstance = new VolleySingle();

    public static VolleySingle getInstance() {
        return ourInstance;
    }

    private VolleySingle() {
        // 获取Application里面的context
        queue = getQueue();// 初始化我的请求队列
    }

    // 提供一个方法新建请求队列
    public RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(MyApplication.context);
        }
        return queue;
    }

    public static final String TAG = "VolleySingleton";

    // 添加请求
    public <T> void addRequest(Request<T> request) {
        request.setTag(TAG);// 为我的请求添加标签, 方便管理
        queue.add(request);// 将请求添加到队列当中
    }

    public <T> void addRequest(Request<T> request, Object tag) {
        request.setTag(tag);
        queue.add(request);
    }

    public void _addRequest(String url,
                            // 这里为我的成功时候的回调加上String类型的泛型
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(url, listener, errorListener);
        addRequest(stringRequest);
    }

    public <T> void _addRequest(String url,
                                Class<T> mClass,
                                Response.Listener<T> listener,
                                Response.ErrorListener errorListener) {
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, url, errorListener, listener, mClass);
        addRequest(gsonRequest);

    }

    // 这个方法是讲请求队列移除队列
    public void removeRequest(Object tag) {
        queue.cancelAll(tag);// 根据不同的tag移除出队列
    }

    public static void addRequest(String url, Response.Listener<String> listener,
                                  Response.ErrorListener errorListener) {
        getInstance()._addRequest(url, listener, errorListener);
    }

    public static <T> void addRequest(String url, Class<T> mClass, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        getInstance()._addRequest(url, mClass, listener, errorListener);
    }

}
