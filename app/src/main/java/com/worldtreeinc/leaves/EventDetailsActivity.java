package com.worldtreeinc.leaves;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ProgressView;

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    // declare class variables
    String eventId;
    FloatingActionButton addItemButton;
    Button enterEventButton;
    ItemListFragment itemListFragment;
    ItemFormFragment itemFormFragment;
    private boolean mShowingBack = false;
    Bundle bundle;
    Banner banner;
    TextView errorMessageHolder;
    Event event;
    private boolean isPlanner;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        bundle = new Bundle();
        eventId = getIntent().getExtras().getString("OBJECT_ID");
        isPlanner = getIntent().getExtras().getBoolean("IS_PLANNER");

        itemListFragment = new ItemListFragment();
        bundle.putString("eventId", eventId);
        bundle.putBoolean("isPlanner", isPlanner);
        itemListFragment.setArguments(bundle);

        banner = new Banner();
        set(eventId);

        init(savedInstanceState);

        NetworkUtil.setError(this, errorMessageHolder, getString(R.string.event_detail_internet_error));

    }


    private void init(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            /* If there is no saved instance state, add a fragment representing the
              front of the card to this activity. If there is saved instance state,
             this fragment will have already been added to the activity. */
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, itemListFragment)
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        addItemButton = (FloatingActionButton) findViewById(R.id.add_item_button);
        enterEventButton = (Button) findViewById(R.id.enterEventButton);

        if (isPlanner) {
            enterEventButton.setVisibility(View.GONE);
        }
        else {
            // call method to check whether bidder has entered the event
            checkBidderAccess();
        }
        errorMessageHolder = (TextView) findViewById(R.id.no_internet_error);

        addItemButton.setOnClickListener(this);
    }

    private void checkBidderAccess() {
        addItemButton.setVisibility(View.GONE);
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            mShowingBack = false;
            return;
        }
        itemFormFragment = new ItemFormFragment();
        itemFormFragment.setArguments(bundle);
        itemFormFragment.setResource(addItemButton);

        mShowingBack = true;

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.container, itemFormFragment)
                .addToBackStack(null)
                .commit();
    }

    public void setViewText(TextView textView, Event event, String fieldName) {
        textView.setText(event.getField(fieldName));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void set(final String eventId) {
        final FrameLayout loader_frame = (FrameLayout) findViewById(R.id.event_details_frame_layout);
        final com.rey.material.widget.ProgressView loader = (com.rey.material.widget.ProgressView) findViewById(R.id.event_details_loading);

        AsyncTask<Void, Void, Void> getOne = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loader.start();
            }

            @Override
            protected Void doInBackground(Void... params) {
                event = Event.getOne(eventId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setData(loader_frame, loader);
            }
        };
        getOne.execute();
    }

    private void setData(final FrameLayout loader_frame, final ProgressView loader) {
        if (event != null) {
            // set Activity title to event title
            setTitle(event.getString("eventName"));

            // set other textview details
            TextView category = (TextView) findViewById(R.id.ed_category_text);
            setViewText(category, event, "eventCategory");
            TextView location = (TextView) findViewById(R.id.ed_location_text);
            setViewText(location, event, "eventVenue");
            TextView date = (TextView) findViewById(R.id.ed_date_text);
            setViewText(date, event, "eventDate");
            // set banner image
            ParseImageView banner = (ParseImageView) findViewById(R.id.event_details_banner);

            banner.setParseFile(event.getBanner());
            banner.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    loader_frame.setVisibility(View.GONE);
                    loader.stop();
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                new ItemImage().set(this, data.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Please select an image!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        flipCard();
        addItemButton.setVisibility(v.GONE);
    }

    public void startPaymentActivity(View v) {

        Intent intent = new Intent(getApplicationContext(), PaymentOptionActivity.class);
        intent.putExtra("event_id", eventId);
        startActivity(intent);
    }
}
