package com.worldtreeinc.leaves;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

/**
 * Created by kamiye on 8/27/15.
 */
public class ItemListFragmentTest extends ActivityInstrumentationTestCase2<EventDetailsActivity> {

    private ItemListFragment listFragment;
    private EventDetailsActivity activity;
    private ListView listView;

    public ItemListFragmentTest() {
        super(EventDetailsActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        listFragment = new ItemListFragment();

        Intent intent = new Intent();
        intent.putExtra("EVENT_ID", "string");
        setActivityIntent(intent);
        activity = getActivity();

        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        transaction.add(R.id.items_list, listFragment);
        transaction.commit();
        getInstrumentation().waitForIdleSync();

        listView = (ListView) listFragment.getView().findViewById(R.id.items_list);
    }

    public void testPreconditions() {
        assertNotNull(activity);
        assertNotNull(listFragment);
        //assertNotNull(listView);
    }

    public void tearDown() throws Exception {

    }
}
