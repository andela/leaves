package com.worldtreeinc.leaves;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityTestCase;

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
}
