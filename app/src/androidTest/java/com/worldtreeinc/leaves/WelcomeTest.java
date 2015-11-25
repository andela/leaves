package com.worldtreeinc.leaves;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.worldtreeinc.leaves.activity.LoginActivity;
import com.worldtreeinc.leaves.activity.WelcomeActivity;

/**
 * Created by andela on 7/16/15.
 */
public class WelcomeTest extends ActivityInstrumentationTestCase2<WelcomeActivity> {

    public WelcomeTest() {
        super(WelcomeActivity.class);
    }

    //test to see if activity exists
    public void testActivityExists() {
        WelcomeActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testLoginBtn() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);
        WelcomeActivity activity = getActivity();

        final Button loginBtn = (Button) activity.findViewById(R.id.loginBtn);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginBtn.performClick();
            }
        });

        LoginActivity nextActivity = (LoginActivity) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }
}
