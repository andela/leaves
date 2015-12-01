package com.worldtreeinc.leaves;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.worldtreeinc.leaves.model.EventItem;

/**
 * Created by kamiye on 8/26/15.
 */
public class EventItemsTest extends InstrumentationTestCase {

    EventItem mItem;
    ParseFile image = new ParseFile("image".getBytes());


    @Override
    public void setUp() {
        mItem = new EventItem();

        mItem.setName("item");
        mItem.setDescription("description");
        mItem.setNewBid(20);
        mItem.setPreviousBid(10);
        mItem.setImage(image);
    }

    @SmallTest
    public void testGetName() {
        String expected = "item";
        String actual = mItem.getName();

        assertEquals("Item names not equal", expected, actual);
    }

    @SmallTest
    public void testGetDescription() {
        String expected = "description";
        String actual = mItem.getDescription();

        assertEquals("Item description not equal", expected, actual);
    }

    @SmallTest
    public void testGetNewBid() {
        int expected = 20;
        int actual = mItem.getNewBid().intValue();

        assertEquals("New Bids field not equal", expected, actual);
    }

    @SmallTest
    public void testGetPreviousBid() {
        int expected = 10;
        int actual = mItem.getPreviousBid().intValue();

        assertEquals("Previous Bids field not equal", expected, actual);
    }

    @SmallTest
    public void testGetImage() {
        ParseFile expected = image;
        ParseFile actual = mItem.getImage();

        assertSame("Images not equal", expected, actual);
    }

    @Override
    public void tearDown() {
        try {
            mItem.delete();
        }
        catch(ParseException e) {
           e.printStackTrace();
        }
    }

}
