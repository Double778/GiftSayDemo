package com.zhao.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest("http://api.liwushuo.com/v2/collections/187/posts?limit=20&offset=0", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MainActivity", response);
                Gson gson = new Gson();
                Bean bean = gson.fromJson(response, Bean.class);
                Log.d("MainActivity", bean.getData().getCover_image_url());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }
}
