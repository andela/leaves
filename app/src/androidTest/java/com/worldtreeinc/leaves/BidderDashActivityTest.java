package com.worldtreeinc.leaves;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * Created by andela on 9/9/15.
 */
public class BidderDashActivityTest extends ActivityInstrumentationTestCase2<BidderDashActivity> {
    public BidderDashActivityTest() {
        super(BidderDashActivity.class);
    }

    //test to see if activity exists
    public void testActivityExists() {
        BidderDashActivity activity = getActivity();
        assertNotNull(activity);
    }

    //test bidder role button
    public void testBrowseEventBtn() {
        //Initialize activity monitor on BidderDashActivity
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(BidderEventListActivity.class.getName(), null, false);
        //current activity
        final BidderDashActivity activity = getActivity();

        final Button browseEventBtn = (Button) activity.findViewById(R.id.browse_event_btn);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                browseEventBtn.performClick();
            }
        });
        //next activity after button click
        BidderEventListActivity nextActivity = (BidderEventListActivity) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    //test planner role button
    public void testMyBidsBtn() {
        final BidderDashActivity activity2 = getActivity();
        final Button myBidsBtn = (Button) activity2.findViewById(R.id.my_bids_btn);
        assertNotNull(myBidsBtn);
    }

}
