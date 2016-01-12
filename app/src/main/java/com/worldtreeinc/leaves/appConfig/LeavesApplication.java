package com.worldtreeinc.leaves.appConfig;

import android.app.Application;

import com.worldtreeinc.leaves.utility.ContextProvider;

/**
 * Created by kamiye on 10/12/15.
 */
public class LeavesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize ContextProvider
        new ContextProvider(this);
    }
}
