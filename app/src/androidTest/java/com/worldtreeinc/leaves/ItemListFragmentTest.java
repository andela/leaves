package com.worldtreeinc.leaves;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;

import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamiye on 8/27/15.
 */
public class ItemListFragmentTest extends ActivityInstrumentationTestCase2<EventDetailsActivity> {

    private ItemListFragment listFragment = new ItemListFragment();;
    private EventDetailsActivity activity;

    // define list of objects
    private List<EventItem> itemLists = new ArrayList<EventItem>();

    // event items addition
    private EventItem eventItem;

    // define image bytes to be used
    private ParseFile image = new ParseFile("image".getBytes());

    // define adapter
    private EventDetailItemListAdapter adapter;

    // define adapter return view globally
    private View view;

    // define view contents
    private TextView itemName;
    private TextView itemDescription;
    private CircularImageView itemImage;
    private TextView itemPreviousBid;
    private TextView itemNewBid;

    //private ListView listView;

    public ItemListFragmentTest() {
        super(EventDetailsActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent();
        intent.putExtra("EVENT_ID", "string");
        setActivityIntent(intent);
        activity = getActivity();

        // instantiate new EventItem object
        eventItem = new EventItem();

        // fill the objects properties of the new event item object
        eventItem.setName("itemName");
        eventItem.setDescription("itemDescription");
        eventItem.setPreviousBid(20);
        eventItem.setNewBid(40);
        eventItem.setImage(image);

        // add item to the list
        itemLists.add(eventItem);

        // set adapter
        adapter = new EventDetailItemListAdapter(activity, itemLists);

        view = adapter.getView(0, null, null);

        // define fragment and add it to the activity

        activity.getFragmentManager()
                .beginTransaction()
                .add(R.id.container, listFragment)
                .commit();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getFragmentManager().executePendingTransactions();
            }
        });

    }

    public void testPreconditions() {
        assertNotNull(activity);
        assertNotNull(listFragment);
        assertNotNull(view);
    }

    public void testFragmentListView_notnull() {
        final ListView listView = (ListView) activity.findViewById(R.id.items_list);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);
            }
        });
        this.getInstrumentation().waitForIdleSync();

        int expected = 1;
        int actual = listView.getCount();

        assertEquals("ListView items error", expected, actual);
    }

    public void testFragment_added() {
        this.getInstrumentation().waitForIdleSync();
        assertTrue(listFragment.isAdded());
    }

    public void testAdapterView_contents() {

        itemName = (TextView) view.findViewById(R.id.event_details_item_name);
        itemDescription = (TextView) view.findViewById(R.id.event_details_item_description);
        itemImage = (CircularImageView) view.findViewById(R.id.event_details_item_image);
        itemNewBid = (TextView) view.findViewById(R.id.event_details_new_bid);
        itemPreviousBid = (TextView) view.findViewById(R.id.event_details_previous_bid);

        // define expected and actual variables to be reused
        String expected;
        String actual;
        Bitmap imageBitmap;
        Bitmap actualImageBitmap;
        byte[] imageBytes;
        int expectedBid;
        int actualBid;


        // item name check
        expected = "itemName";
        actual = itemName.getText().toString();

        assertEquals("Item Name error", expected, actual);


        // item description check
        expected = "itemDescription";
        actual = itemDescription.getText().toString();

        assertEquals("Item Description error", expected, actual);


        // item image check
        try {
            imageBytes = eventItem.getImage().getData();
            imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        catch (ParseException e) {
            e.printStackTrace();
            imageBitmap = null;
        }

        actualImageBitmap = itemImage.getDrawingCache();

        assertEquals("Item Image error", imageBitmap, actualImageBitmap);


        // item new bid check
        expectedBid = 40;
        actualBid = Integer.parseInt(itemNewBid.getText().toString());

        assertEquals("Item New Bid error", expectedBid, actualBid);


        // item previous bid check
        expectedBid = 20;
        actualBid = Integer.parseInt(itemPreviousBid.getText().toString());

        assertEquals("Item Previous Bid error", expectedBid, actualBid);

    }

    public void tearDown() throws Exception {
        activity.getFragmentManager()
                .beginTransaction()
                .detach(listFragment)
                .commit();
        activity.finish();
        eventItem.delete();
        itemLists.remove(eventItem);
        adapter = null;
        view = null;
    }
}
