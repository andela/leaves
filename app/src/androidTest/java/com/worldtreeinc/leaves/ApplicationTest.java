package com.worldtreeinc.leaves;

import android.app.Activity;
import android.app.Application;
import android.test.ApplicationTestCase;

import com.rey.material.widget.TextView;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    Activity activity;
    public ApplicationTest() {
        super(Application.class);
        TextView textView = (TextView) activity.findViewById(R.id.textView4);
        assertNotNull(textView);
    }

    public void testLoginText(){
        TextView textView = (TextView) activity.findViewById(R.id.textView4);
        assertNotNull(textView);
    }

}