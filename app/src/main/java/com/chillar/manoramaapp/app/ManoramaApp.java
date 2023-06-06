package com.chillar.manoramaapp.app;

import android.app.Application;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * Application class
 */

public class ManoramaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);
        /*
        * initializing connection buddy in App class
        * */
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }
}
