package com.worldtreeinc.leaves.appConfig;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.model.EventItem;
import com.worldtreeinc.leaves.model.Payment;
import com.worldtreeinc.leaves.model.User;
import com.worldtreeinc.leaves.model.User;

public class ParseApplication extends LeavesApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);
        // Enable Local Data store.
        Parse.enableLocalDatastore(this);

        //register subclasses
        ParseObject.registerSubclass(EventItem.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(Payment.class);

        // Add your initialization code here
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();


        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }

}
