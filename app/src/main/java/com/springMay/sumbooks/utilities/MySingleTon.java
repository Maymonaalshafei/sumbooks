package com.springMay.sumbooks.utilities;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.collection.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleTon extends Application {

    private static MySingleTon instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context ctx;

    public MySingleTon(){}
    //
    private MySingleTon(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }
    //
    public static synchronized MySingleTon getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleTon(context);
        }
        return instance;
    }
    //
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }
    //
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
    //
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}


