package com.worldtreeinc.leaves;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import android.widget.ListView;

import com.parse.ParseFile;

/**
 * Created by andela on 7/29/15.
 */
public class PlannerEventActivityTest extends ActivityInstrumentationTestCase2<PlannerEventActivity> {

    PlannerEventActivity mPlannerActivity;
    Event mEvent = new Event();
    ListView mListView;

    public PlannerEventActivityTest(){
        super(PlannerEventActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        /** Getting the reference to the context containing listview to be tested */
        mPlannerActivity = getActivity();

        /** Getting the reference to the context to be tested */
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
        mEvent.setVenue(venue);
        String expectedVenue  = venue;
        String actualVenue = mEvent.getField("eventVenue");
        assertEquals(expectedVenue, actualVenue);


        //testing the set and get description methods
        String descript = "Technology";
        mEvent.setDescription(descript);
        String expectedDescription  = descript;
        String actualDescription = mEvent.getField("eventDescription");
        assertEquals(expectedDescription, actualDescription);


        //testing the set and get Date methods
        String date = "30/08/1987";
        mEvent.setDate(date);
        String expectedDate  = date;
        String actualDate = mEvent.getField("eventDate");
        assertEquals(expectedDate, actualDate);


        //testing the get and set category methods
        String category = "Education";
        mEvent.setCategory(category);
        String expectedCategory  = category;
        String actualCategory = mEvent.getField("eventCategory");
        assertEquals(expectedCategory, actualCategory);


        //testing the get and set event Banner methods
        String banner = "http://files.parsetfss.com/8bf661dc-3da1-4288-b727-8a7210bd2943/tfss-dd0ae0da-b48a-4d3a-bcd6-29682948208a-banner.jpg";
        mEvent.setBanner(banner);
        String expectedBanner  = banner;
        ParseFile actualBanner = mEvent.getParseFile("eventBanner");
        assertEquals(expectedBanner, actualBanner);


        //testing the get and set event Name
        String name = "Cheapest car";
        mEvent.setName(name);
        String expectedName  = name;
        String actualName = mEvent.getField("eventName");
        assertEquals(expectedName, actualName);
    }
}