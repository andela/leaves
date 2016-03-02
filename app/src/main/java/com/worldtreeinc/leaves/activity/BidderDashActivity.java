package com.worldtreeinc.leaves.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.adapter.EventsListAdapter;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.model.User;
import com.worldtreeinc.leaves.utility.ActivityLauncher;

import java.util.ArrayList;


public class BidderDashActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView eventList;
    private EventsListAdapter listAdapter;
    private ProgressView loader;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoggedInUser();
        setContentView(R.layout.activity_bidder_dash);

        initialize();
        new ItemAsyncTask().execute();
    }

    private void initialize(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.bidder_dashboard_title));

        Button browseEvent = (Button) findViewById(R.id.browse_event_btn);
        browseEvent.setOnClickListener(this);
        eventList = (ListView) findViewById(R.id.event_list);
        frame = (FrameLayout)findViewById(R.id.frame_loader);
        loader = (ProgressView) this.findViewById(R.id.loading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bidder_dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                Toast.makeText(this, "Logging out", Toast.LENGTH_LONG).show();
                User.logout();
                ActivityLauncher.runIntent(this, WelcomeActivity.class);
                return true;
            default:
                return false;
        }
    }

    private class ItemAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Event event = Event.getFirst();
            listAdapter.clear();
            if (event != null) listAdapter.add(event);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listAdapter = new EventsListAdapter(BidderDashActivity.this, new ArrayList<Event>(), false);
            loader.start();
        }

        @Override
        protected void onPostExecute(Void result) {
            eventList.setAdapter(listAdapter);
            frame.setVisibility(View.GONE);
            loader.stop();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.browse_event_btn :
                startActivity(new Intent(this, BidderEventListActivity.class));
                break;
        }
    }

    private void checkLoggedInUser() {
        if (!User.isLoggedIn()) {
            Intent getStartedIntent = new Intent(this, GetStartedActivity.class);
            startActivity(getStartedIntent);
        }
    }
}
