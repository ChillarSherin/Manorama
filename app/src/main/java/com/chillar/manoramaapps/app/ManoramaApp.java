package com.chillar.manoramaapps.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * Application class
 */

public class ManoramaApp extends Application {

    public static final String TAG = ManoramaApp.class.getSimpleName();
    private static ManoramaApp mInstance;
    private RequestQueue mRequestQueue;

    static Context mContext;

    public static Context getContext() {
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        mInstance = this;
        CustomActivityOnCrash.install(this);
        /*
        * initializing connection buddy in App class
        * */
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }

    public static synchronized ManoramaApp getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(getApplicationContext(),commonSSLConnection.getHulkstack(getApplicationContext()));
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        ///Eldhose

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
