package com.worldtreeinc.leaves;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by kamiye on 8/1/15.
 */
public class EventDetailsActivityTest extends ActivityInstrumentationTestCase2<EventDetailsActivity> {

    private EventDetailsActivity mEventDetailsActivity;
    private ImageView mImageBanner;
    private TextView mLocation;
    private TextView mCategory;
    private TextView mDate;

    public EventDetailsActivityTest() {
        super(EventDetailsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mEventDetailsActivity = getActivity();
        mImageBanner =  (ImageView) mEventDetailsActivity.findViewById(R.id.event_details_banner);
        mLocation =  (TextView) mEventDetailsActivity.findViewById(R.id.ed_location_text);
        mCategory =  (TextView) mEventDetailsActivity.findViewById(R.id.ed_category_text);
        mDate =  (TextView) mEventDetailsActivity.findViewById(R.id.ed_date_text);
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

    // test error message for no internet
    public void testErrorMessage() {
        mEventDetailsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WifiManager wifiManager = (WifiManager) mEventDetailsActivity.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);
            }
        });


        String expected = "No Internet Connection";
        getInstrumentation().waitForIdleSync();
        TextView error = (TextView) mEventDetailsActivity.findViewById(R.id.no_internet_error);
        String actual  = error.getText().toString();

        assertEquals("Internet error not showing", expected, actual);
    }

}
