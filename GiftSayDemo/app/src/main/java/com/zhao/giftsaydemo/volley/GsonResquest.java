package com.zhao.giftsaydemo.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by 华哥哥 on 16/5/11.
 */
public class GsonResquest<T> extends Request<T> {
    private Response.Listener<T> mListener;
    private Gson mGson;
    private Class<T> mClass;

    public GsonResquest(int method, String url, Response.ErrorListener listener, Response.Listener<T> mListener, Class<T> mClass) {
        super(method, url, listener);
        this.mListener = mListener;
        this.mGson = new Gson();
        this.mClass = mClass;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(data, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
