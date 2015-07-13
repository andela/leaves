package com.worldtreeinc.leaves;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
<<<<<<< HEAD
=======
import com.parse.ParseObject;
>>>>>>> Initial folder structure
import com.parse.ParseUser;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

<<<<<<< HEAD
        // Enable Local Data store.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
=======
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code heree
>>>>>>> Initial folder structure
        Parse.initialize(this);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
