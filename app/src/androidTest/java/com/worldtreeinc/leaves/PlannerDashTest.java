package com.worldtreeinc.leaves;


import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class PlannerDashTest extends ActivityInstrumentationTestCase2<PlannerDashActivity> {
    public PlannerDashTest() {
        super(PlannerDashActivity.class);
    }

    //test to see if activity exists
    public void testActivityExists() {
        PlannerDashActivity activity = getActivity();
        assertNotNull(activity);
    }

    //test bidder role button
    public void testCreateEventBtn() {
        //Initialize activity monitor on BidderDashActivity
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(EventActivity.class.getName(), null, false);
        //current activity
        final PlannerDashActivity activity = getActivity();

        final Button createEventBtn = (Button) activity.findViewById(R.id.create_event_btn);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createEventBtn.performClick();
            }
        });
        //next activity after button click
        EventActivity nextActivity = (EventActivity) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    //test planner role button
    public void testManageEventBtn() {
        //Initialize activity monitor on BidderDashActivity
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PlannerEventActivity.class.getName(), null, false);
        //current activity
        PlannerDashActivity activity = getActivity();

        final Button manageEventBtn = (Button) activity.findViewById(R.id.manage_events_btn);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                manageEventBtn.performClick();
            }
        });
        //next activity after button click
        PlannerEventActivity nextActivity = (PlannerEventActivity) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    public void testBidModel() {
        EventItem bid = new EventItem();

        String eventName = "Club auction";
        bid.setEventName(eventName);
        String expectedName = eventName;
        String actualName = bid.getEventName();
        assertEquals(expectedName, actualName);

    }
}
