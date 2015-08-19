package com.worldtreeinc.leaves;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.parse.ParseFile;

/**
 * Created by andela on 8/3/15.
 */
public class EventTest extends InstrumentationTestCase {

    Event mUserEvent;

    @SmallTest
    public void testEvent(){

        mUserEvent = new Event();

        //testing the set and get venue methods
        String venue = "Amity";
        mUserEvent.setVenue(venue);
        String expectedVenue  = venue;
        String actualVenue = mUserEvent.getField("eventVenue");
        assertEquals(expectedVenue, actualVenue);


        //testing the set and get description methods
        String descript = "Technology";
        mUserEvent.setDescription(descript);
        String expectedDescription  = descript;
        String actualDescription = mUserEvent.getField("eventDescription");
        assertEquals(expectedDescription, actualDescription);


        //testing the set and get Date methods
        String date = "30/08/1987";
        mUserEvent.setDate(date);
        String expectedDate  = date;
        String actualDate = mUserEvent.getField("eventDate");
        assertEquals(expectedDate, actualDate);


        //testing the get and set category methods
        String category = "Education";
        mUserEvent.setCategory(category);
        String expectedCategory  = category;
        String actualCategory = mUserEvent.getField("eventCategory");
        assertEquals(expectedCategory, actualCategory);


        //testing the get and set event Banner methods
        ParseFile banner = new ParseFile("String".getBytes());
        mUserEvent.setBanner(banner);
        ParseFile expectedBanner  = banner;
        ParseFile actualBanner = mUserEvent.getBanner();
        assertEquals(expectedBanner, actualBanner);


        //testing the get and set event Name
        String name = "Cheapest car";
        mUserEvent.setName(name);
        String expectedName  = name;
        String actualName = mUserEvent.getField("eventName");
        assertEquals(expectedName, actualName);
    }

}
