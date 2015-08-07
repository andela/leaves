package com.worldtreeinc.leaves;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;

import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Data store.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this);

        //register subclasses
        ParseObject.registerSubclass(BidModel.class);
        ParseObject.registerSubclass(Event.class);

        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
