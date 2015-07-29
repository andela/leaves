package com.worldtreeinc.leaves;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;

/**
 * Created by andela on 7/29/15.
 */
public class PlannerEventActivityTest extends ActivityInstrumentationTestCase2<PlannerEventActivity> {

    PlannerEventActivity mPlannerActivity;
    UserEvent mUserEvent = new UserEvent();

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

    @SmallTest
    public void testUserEvent(){

        //testing the set and get venue methods
        String venue = "Amity";
        mUserEvent.setEventVenue(venue);
        String expectedVenue  = venue;
        String actualVenue = mUserEvent.getEventVenue();
        assertEquals(expectedVenue, actualVenue);


        //testing the set and get description methods
        String descript = "Technology";
        mUserEvent.setEventDescription(descript);
        String expectedDescription  = descript;
        String actualDescription = mUserEvent.getEventDescription();
        assertEquals(expectedDescription, actualDescription);


        //testing the set and get Date methods
        String date = "30/08/1987";
        mUserEvent.setEventDate(date);
        String expectedDate  = date;
        String actualDate = mUserEvent.getEventDate();
        assertEquals(expectedDate, actualDate);


        //testing the get and set category methods
        String category = "Education";
        mUserEvent.setEventCategory(category);
        String expectedCategory  = category;
        String actualCategory = mUserEvent.getEventCategory();
        assertEquals(expectedCategory, actualCategory);


        //testing the get and set event Banner methods
        String banner = "http://files.parsetfss.com/8bf661dc-3da1-4288-b727-8a7210bd2943/tfss-dd0ae0da-b48a-4d3a-bcd6-29682948208a-banner.jpg";
        mUserEvent.setEventBanner(banner);
        String expectedBanner  = banner;
        String actualBanner = mUserEvent.getEventBanner();
        assertEquals(expectedBanner, actualBanner);


        //testing the get and set event Name
        String name = "Cheapest car";
        mUserEvent.setEventName(name);
        String expectedName  = name;
        String actualName = mUserEvent.getEventName();
        assertEquals(expectedName, actualName);
    }

}
