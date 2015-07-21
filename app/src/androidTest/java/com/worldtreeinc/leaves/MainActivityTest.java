package com.worldtreeinc.leaves;

import android.test.ActivityInstrumentationTestCase2;
import com.rey.material.widget.ProgressView;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    // constructor
    public MainActivityTest() {
        super(MainActivity.class);
    }

    // test for mainActivity
    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    // test for loader
    public void testLoader() {
        MainActivity activity = getActivity();
        final ProgressView loader = (ProgressView) activity.findViewById(R.id.splashLoader);
        assertNotNull(loader);
    }
}
