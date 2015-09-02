package com.worldtreeinc.leaves;

import android.app.Activity;
import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 7/29/15.
 */

public class EventsListAdapterTest extends InstrumentationTestCase {

    private List<Event> data = new ArrayList<Event>();
    private EventsListAdapter mAdapter;
    Event event;

    public EventsListAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        event = new Event();
        event.setDescription("Technology");
        event.setVenue("Amity");
        event.setName("cheapest car");
        event.setCategory("Bizz");
        event.setDate("30/08/1987");
        data.add(event);
        mAdapter = new EventsListAdapter((Activity) getInstrumentation().getContext(), data, true);
    }

    public void testGetItemId() {
        assertEquals("Wrong ID.", 0, mAdapter.getItemId(0));
    }

    public void testGetCount() {
        assertEquals("Events count incorrect.", 1, mAdapter.getCount());
    }
}

