package com.worldtreeinc.leaves;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.rey.material.widget.Button;

import com.parse.ParseException;

import com.parse.ParseFile;

/**
 * Created by kamiye on 8/27/15.
 */
public class ItemFormFragmentTest extends ActivityInstrumentationTestCase2<EventDetailsActivity> {

    private ItemFormFragment formFragment = new ItemFormFragment();
    private EventDetailsActivity activity;

    // define image bytes to be used
    private ParseFile image = new ParseFile("image".getBytes());

    // define form fields
    private EditText itemName;
    private EditText itemStartingBid;
    private EditText itemDescription;
    private Button itemSaveButton;
    private Button itemCancelButton;
    private ImageView itemImage;

    public ItemFormFragmentTest() {
        super(EventDetailsActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent();
        intent.putExtra("EVENT_ID", "string");
        setActivityIntent(intent);
        activity = getActivity();

        // define fragment and add it to the activity
        Bundle bundle = new Bundle();
        bundle.putString("eventId", null);
        bundle.putString("itemId", null);
        formFragment.setArguments(bundle);

        activity.getFragmentManager()
                .beginTransaction()
                .add(R.id.container, formFragment)
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
        assertNotNull(formFragment);
    }

    public void testFragment_added() {
        this.getInstrumentation().waitForIdleSync();
        assertTrue(formFragment.isAdded());
    }

    public void testAdapterView_contents() {

        this.getInstrumentation().waitForIdleSync();

        itemName = (EditText) activity.findViewById(R.id.new_item_name);
        itemDescription = (EditText) activity.findViewById(R.id.new_item_description);
        itemStartingBid = (EditText) activity.findViewById(R.id.new_item_start_bid);
        itemSaveButton = (Button) activity.findViewById(R.id.confirm_add_item_button);
        itemCancelButton = (Button) activity.findViewById(R.id.cancel_add_item_button);
        itemImage = (ImageView) activity.findViewById(R.id.new_item_image);

        // define expected and actual variables to be reused
        String expected;
        String actual;

        Drawable drawable;
        /*Bitmap imageBitmap;
        Bitmap actualImageBitmap;
        byte[] imageBytes;*/
        int expectedBid;
        int actualBid;

        // send item name to text to EditText
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                itemName.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("itemName");

        // send item description to text to EditText
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                itemDescription.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("itemDescription");

        // send item starting bid text to EditText
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                itemStartingBid.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("20");


        // item name check
        expected = "itemName";
        actual = itemName.getText().toString();

        assertEquals("Item Name error", expected, actual);


        // item name check
        expected = "itemDescription";
        actual = itemDescription.getText().toString();

        assertEquals("Item Description error", expected, actual);


        // item image check
        assertNotNull("Image Resource error", itemImage.getDrawable());


        // item starting bid check
        expectedBid = 20;
        actualBid = Integer.parseInt(itemStartingBid.getText().toString());

        assertEquals("Item Starting Bid error", expectedBid, actualBid);


        // item cancel button text check
        expected = "Cancel";
        actual = itemCancelButton.getText().toString();

        assertEquals("Item Cancel Button error", expected, actual);


        // item save button check
        expected = "Save";
        actual = itemSaveButton.getText().toString();

        assertEquals("Item Save Button error", expected, actual);
    }

    public void tearDown() throws Exception {
        activity.getFragmentManager()
                .beginTransaction()
                .detach(formFragment)
                .commit();
        activity.finish();
    }
}
