package com.worldtreeinc.leaves.helper;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by andela on 02/03/2016.
 */
public class MyToolbar {

    public static void setToolbar(AppCompatActivity activity) {
        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
