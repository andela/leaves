package com.worldtreeinc.leaves;


import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class RoleOptionTest extends ActivityInstrumentationTestCase2<RoleOptionActivity> {
    public RoleOptionTest() {
        super(RoleOptionActivity.class);
    }

    //test to see if activity exists
    public void testActivityExists() {
        RoleOptionActivity activity = getActivity();
        assertNotNull(activity);
    }

    //test bidder role button
    public void testBidderBtn() {
        //Initialize activity monitor on BidderDashActivity
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(BidderDashActivity.class.getName(), null, false);
        //current activity
        RoleOptionActivity activity = getActivity();

        final Button bidderBtn = (Button) activity.findViewById(R.id.bidderBtn);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bidderBtn.performClick();
            }
        });
        //next activity after button click
        BidderDashActivity nextActivity = (BidderDashActivity) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    //test planner role button
    public void testPlannerBtn() {
        //Initialize activity monitor on BidderDashActivity
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PlannerDashActivity.class.getName(), null, false);
        //current activity
        RoleOptionActivity activity = getActivity();

        final Button plannerBtn = (Button) activity.findViewById(R.id.plannerBtn);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                plannerBtn.performClick();
            }
        });
        //next activity after button click
        PlannerDashActivity nextActivity = (PlannerDashActivity) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }
}
