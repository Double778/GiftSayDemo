package com.zhao.giftsaydemo.volley;


import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zhao.giftsaydemo.base.MyApplication;
import com.zhao.giftsaydemo.home.bean.TabBean;

/**
 * Created by 华哥哥 on 16/5/10.
 */
public class VolleyTool {

    private  RequestQueue requestQueue;
    private TabBean tabBean;

    public VolleyTool() {
        requestQueue = Volley.newRequestQueue(MyApplication.context);
    }

    public TabBean JsonTabData(String url){
        StringRequest  stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                tabBean = gson.fromJson(response, TabBean.class);
                //Log.d("VolleyTool", "tabBean:---" + tabBean.getData().getChannels().get(0).getName());
                Log.d("VolleyTool", "tabBean:++++++" + tabBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyTool", "失败");
            }
        });

        requestQueue.add(stringRequest);
        Log.d("VolleyTool", "tabBean:---" + tabBean+"");
        return tabBean;
    }


}

