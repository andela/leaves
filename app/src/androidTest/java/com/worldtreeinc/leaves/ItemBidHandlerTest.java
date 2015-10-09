package com.worldtreeinc.leaves;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;



/**
 * Created by andela on 10/2/15.
 */
public class ItemBidHandlerTest extends ActivityInstrumentationTestCase2<PlannerEventActivity> {

    public ItemBidHandlerTest() {
        super(PlannerEventActivity.class);
    }
    public void testActivity(){
        PlannerEventActivity plannerEventActivity = getActivity();
        assertNotNull(plannerEventActivity);
    }

    public void testEventClick() {
//        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(EventDetailsActivity.class.getName(), null, false);
//        final PlannerEventActivity plannerEventActivity = getActivity();
//        plannerEventActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });

        // final PlannerEventActivity plannerEventActivity = getActivity();
        Intent intent = new Intent();
        intent.putExtra("OBJECT_ID", "VG9xAs6fuG");
        intent.putExtra("IS_PLANNER", true);
        intent.setClassName(getInstrumentation().getTargetContext(), EventDetailsActivity.class.getName());
        getInstrumentation().startActivitySync(intent);
    }
}
