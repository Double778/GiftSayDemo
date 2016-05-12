package com.zhao.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import it.sephiroth.android.library.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.iv);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        GsonRequest<Bean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://api.liwushuo.com/v2/channels/103/items? limit=20&ad=2&gender=2&offset=0&generation=1", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new Response.Listener<Bean>() {
            @Override
            public void onResponse(Bean response) {
                Log.d("MainActivity", response.getData().getItems().get(0).getTitle());

            }
        }, Bean.class);
        requestQueue.add(gsonRequest);
        Picasso.with(this).load("http://img02.liwushuo.com/image/151015/7rsmi37a2_w.jpg-w720").placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
    }
}
