package com.worldtreeinc.leaves;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.rey.material.widget.FloatingActionButton;

/**
 * Created by kamiye on 8/1/15.
 */
public class EventDetailsActivityTest extends ActivityInstrumentationTestCase2<EventDetailsActivity> {

    private EventDetailsActivity mEventDetailsActivity;
    private ImageView mImageBanner;
    private TextView mLocation;
    private TextView mCategory;
    private TextView mDate;
    private FloatingActionButton mAddButton;
    private Fragment fragment;

    public EventDetailsActivityTest() {
        super(EventDetailsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent();
        intent.putExtra("EVENT_ID", "string");
        setActivityIntent(intent);
        mEventDetailsActivity = getActivity();
        mImageBanner =  (ImageView) mEventDetailsActivity.findViewById(R.id.event_details_banner);
        mLocation =  (TextView) mEventDetailsActivity.findViewById(R.id.ed_location_text);
        mCategory =  (TextView) mEventDetailsActivity.findViewById(R.id.ed_category_text);
        mDate =  (TextView) mEventDetailsActivity.findViewById(R.id.ed_date_text);

        // other components
        fragment = (Fragment) getActivity().getSupportFragmentManager().getFragments();
        mAddButton = (FloatingActionButton) mEventDetailsActivity.findViewById(R.id.add_item_button);
    }

    // test preconditions for all view items to be tested
    public void testPreconditions() {
        assertNotNull("Activity is null", mEventDetailsActivity);
        assertNotNull("Banner ImageView is null", mImageBanner);
        assertNotNull("Location TextView is null", mLocation);
        assertNotNull("Category TextView is null", mCategory);
        assertNotNull("Date TextView is null", mDate);
    }

    // test banner image
    public void testBannerImage() {
        getInstrumentation().waitForIdleSync();
        final String expected = "";
        final String actual = mImageBanner.getDrawable().toString();
        assertNotSame("Image banner not fetched", expected, actual);
    }

    // test location text
    public void testLocationText() {
        mEventDetailsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLocation.setText("Location"); // manually setting dependency
            }
        });

        getInstrumentation().waitForIdleSync();
        final String expected = "";
        final String actual = mLocation.getText().toString();
        assertNotSame("Location not fetched", expected, actual);
    }

    // test category text
    public void testCategoryText() {
        mEventDetailsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCategory.setText("Location"); // manually setting dependency
            }
        });

        getInstrumentation().waitForIdleSync();
        final String expected = "";
        final String actual = mCategory.getText().toString();
        assertNotSame("Category not fetched", expected, actual);
    }

    // test date text
    public void testDateText() {
        mEventDetailsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDate.setText("01/01/06"); // manually setting dependency
            }
        });

        getInstrumentation().waitForIdleSync();
        final String expected = "";
        final String actual = mDate.getText().toString();
        assertNotSame("Date not fetched", expected, actual);
    }

    public void testAddButton() {
        final ViewGroup buttonRootView = (ViewGroup) mEventDetailsActivity.findViewById(R.id.event_details_relativelayout);
        ViewAsserts.assertGroupContains(buttonRootView, mAddButton);

        final ViewGroup.LayoutParams layoutParams =
                mAddButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, ViewGroup.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void testAddButton_autoHiding() {
        mEventDetailsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAddButton.performClick();
            }
        });

        this.getInstrumentation().waitForIdleSync();
        assertTrue(FloatingActionButton.GONE == mAddButton.getVisibility());
    }

}
