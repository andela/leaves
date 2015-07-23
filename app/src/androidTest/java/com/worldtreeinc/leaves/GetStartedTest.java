package com.worldtreeinc.leaves;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class GetStartedTest extends ActivityInstrumentationTestCase2<GetStartedActivity> {
    public GetStartedTest() {
        super(GetStartedActivity.class);
    }

    public void testActivityExists() {
        Activity activity = getActivity();
        assertNotNull(activity);
    }

    public void testGetStartedButton() {
        Instrumentation.ActivityMonitor nextActivity = getInstrumentation().addMonitor(WelcomeActivity.class.getName(), null, false);
        Activity activity = getActivity();
        final Button getStarted = (Button) activity.findViewById(R.id.get_started);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getStarted.performClick();
            }
        });

        WelcomeActivity welcome = (WelcomeActivity) getInstrumentation().waitForMonitor(nextActivity);
        assertNotNull(welcome);
    }
}
