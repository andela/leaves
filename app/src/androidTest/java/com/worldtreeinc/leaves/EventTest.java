package com.worldtreeinc.leaves;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.parse.ParseFile;

/**
 * Created by andela on 8/3/15.
 */
public class EventTest extends InstrumentationTestCase {

    Event mEvent;

    @SmallTest
    public void testEvent(){

        mEvent = new Event();

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
        ParseFile banner = new ParseFile("String".getBytes());
        mEvent.setBanner(banner);
        ParseFile expectedBanner  = banner;
        ParseFile actualBanner = mEvent.getBanner();
        assertEquals(expectedBanner, actualBanner);


        //testing the get and set event Name
        String name = "Cheapest car";
        mEvent.setName(name);
        String expectedName  = name;
        String actualName = mEvent.getField("eventName");
        assertEquals(expectedName, actualName);
    }

}
