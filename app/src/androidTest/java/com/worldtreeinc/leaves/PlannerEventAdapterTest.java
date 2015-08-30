package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 7/29/15.
 */

public class PlannerEventAdapterTest extends InstrumentationTestCase {

    private List<Event> data = new ArrayList<Event>();
    private PlannerEventAdapter mAdapter;
    Event event;

    public PlannerEventAdapterTest() {
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
        mAdapter = new PlannerEventAdapter((Activity) getInstrumentation().getContext(), data);
    }

    public void testGetItemId() {
        assertEquals("Wrong ID.", 0, mAdapter.getItemId(0));
    }

    public void testGetCount() {
        assertEquals("Events count incorrect.", 1, mAdapter.getCount());
    }
}

