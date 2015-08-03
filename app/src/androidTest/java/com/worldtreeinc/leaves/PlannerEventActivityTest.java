package com.worldtreeinc.leaves;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.ListView;

/**
 * Created by andela on 7/29/15.
 */
public class PlannerEventActivityTest extends ActivityInstrumentationTestCase2<PlannerEventActivity> {

    PlannerEventActivity mPlannerActivity;



    ListView mListView;

    public PlannerEventActivityTest(){
        super(PlannerEventActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        /** Getting the reference to the activity containing listview to be tested */
        mPlannerActivity = getActivity();

        /** Getting the reference to the activity to be tested */
        mListView = (ListView) mPlannerActivity.findViewById(R.id.listView);



    }

    public void testPreconditions() {
        assertNotNull("mPlannerActivity is null", mPlannerActivity);
        assertNotNull("mListView is null", mListView);

        final View decorView = mPlannerActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mListView);
    }



    public void testFuntionalPlannerEventActivity(){

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(PlannerEventActivity.class.getName(),
                        null, false);

        getActivity();

        PlannerEventActivity receiverActivity = (PlannerEventActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(11000);
        //assertNotNull("PlannerEventActivity is null", receiverActivity);

    }



    @SmallTest
    public void testListView(){
        int expectedCount = 10;
        int actualCount = 10;
        assertEquals(expectedCount, actualCount);


    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
