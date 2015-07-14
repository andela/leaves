package com.worldtreeinc.leaves;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
<<<<<<< HEAD
import com.parse.ParseObject;
import com.parse.ParseUser;

=======

import com.parse.ParseUser;
>>>>>>> eb7e4877bcea50a337c7c80c6227d6c8476cf982

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

<<<<<<< HEAD
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code heree
=======
        // Enable Local Data store.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here

>>>>>>> eb7e4877bcea50a337c7c80c6227d6c8476cf982
        Parse.initialize(this);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
