package com.worldtreeinc.leaves;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

public class EventDetailsActivity extends AppCompatActivity {

    // declare class variables
    private ParseItemsAdapter m_adapter;
    String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventId = getIntent().getExtras().getString("OBJECT_ID");
        // create new ParseImageLoader passing in banner to it and eventId
        // and call setImage() method on it

        set(eventId);

        // check internet access
        boolean connecting = checkOnlineState();
        TextView error = (TextView) findViewById(R.id.no_internet_error);
        if (!connecting) {
            error.setText("No Internet Connection");
        }
        else {
            // instantiate m_adapter and pass in Event ID
            m_adapter = new ParseItemsAdapter(this, Event.getOne(eventId));
            m_adapter.notifyDataSetChanged();
            if (m_adapter.getCount() == 0) {
                error.setText("No Items to Display");
            }
            // check if there are no items and display message as necessary
            ListView listView = (ListView) findViewById(R.id.items_list);
            listView.setAdapter(m_adapter);

        }

    }

    public void setViewText(TextView textView, Event event, String fieldName) {
        textView.setText(event.getField(fieldName));
    }

    public void set(String eventId) {
        Event event = Event.getOne(eventId);
        if (event != null) {
            // set Activity title to event title
            setTitle(event.getString("eventName"));

            // set banner image
            ParseImageView banner = (ParseImageView) findViewById(R.id.event_details_banner);
            final FrameLayout loader_frame = (FrameLayout) findViewById(R.id.event_details_frame_layout);
            final com.rey.material.widget.ProgressView loader = (com.rey.material.widget.ProgressView) findViewById(R.id.event_details_loading);
            loader.start();
            banner.setParseFile(event.getBanner());
            banner.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    loader_frame.setVisibility(View.GONE);
                    loader.stop();

                }
            });

            // set other textview details
            TextView category = (TextView) findViewById(R.id.ed_category_text);
            setViewText(category, event, "eventCategory");

            TextView location = (TextView) findViewById(R.id.ed_location_text);
            setViewText(location, event, "eventVenue");

            TextView date = (TextView) findViewById(R.id.ed_date_text);
            setViewText(date, event, "eventDate");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean checkOnlineState() {
        ConnectivityManager CManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NInfo = CManager.getActiveNetworkInfo();
        return (NInfo != null && NInfo.isConnectedOrConnecting());
    }
}