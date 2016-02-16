package com.worldtreeinc.leaves.activity;

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
import com.parse.SaveCallback;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ProgressView;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.fragment.ItemFormFragment;
import com.worldtreeinc.leaves.fragment.ItemListFragment;
import com.worldtreeinc.leaves.model.Banner;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.model.ItemImage;
import com.worldtreeinc.leaves.model.User;
import com.worldtreeinc.leaves.utility.CameraManager;
import com.worldtreeinc.leaves.utility.NetworkUtil;
import com.worldtreeinc.leaves.utility.ParseProxyObject;


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
    String eventName;

    private static int RESULT_LOAD_IMAGE = 1;
    private static int IMAGE_CAPTURE = 3401;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        initializeFragmentObjects();
        checkForConnectivity(savedInstanceState);
    }

    private void initializeFragmentObjects() {
        errorMessageHolder = (TextView) findViewById(R.id.no_internet_error);

        bundle = new Bundle();
        bundle = getIntent().getExtras();
        eventId = bundle.getString(getString(R.string.object_id_reference));
        isPlanner = bundle.getBoolean(getString(R.string.is_planner_reference));

        itemListFragment = new ItemListFragment();
        bundle.putString("eventId", eventId);
        bundle.putBoolean("isPlanner", isPlanner);
        banner = new Banner();
    }

    private void checkForConnectivity(Bundle savedInstanceState) {
        if (NetworkUtil.getConnectivityStatus(this)) {
            set(eventId, savedInstanceState);
        } else {
            errorMessageHolder.setText(getString(R.string.event_detail_internet_error));
        }
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
        } else {
            // call method to check whether bidder has entered the event
            checkBidderAccess();
        }

        addItemButton.setOnClickListener(this);
    }

    private void checkBidderAccess() {
        addItemButton.setVisibility(View.GONE);
        if (User.isEnteredEvent(eventId)) {
            enterEventButton.setVisibility(View.GONE);
        }
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

    public void set(final String eventId, final Bundle savedInstanceState) {
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
                Event newEvent = event;
                eventName = newEvent.getField("eventName");
                bundle.putString("eventName", eventName);
                itemListFragment.setArguments(bundle);
                init(savedInstanceState);
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
            if ((requestCode == RESULT_LOAD_IMAGE || requestCode == IMAGE_CAPTURE)
                    && resultCode == RESULT_OK && data != null) {
                new ItemImage().set(this, CameraManager.getUri(this, data, requestCode));
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
        addItemButton.setVisibility(View.GONE);
    }

    public void startPaymentActivity(View v) {

        if ((int) event.getEntryFee() > 0) {
            Intent intent = new Intent(getApplicationContext(), PaymentOptionActivity.class);
            ParseProxyObject proxyObject = new ParseProxyObject(event);
            Bundle bundle = new Bundle();
            bundle.putSerializable("event", proxyObject);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            User.enterEvent(eventId, new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(EventDetailsActivity.this, "Event entered successfully", Toast.LENGTH_LONG).show();
                    Event.getOne(eventId).incrementEntries();
                    EventDetailsActivity.this.recreate();
                }
            });
        }
    }
}
