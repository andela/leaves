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
import android.widget.ListView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.adapter.PlannerDashItemListAdapter;
import com.worldtreeinc.leaves.model.EventItem;
import com.worldtreeinc.leaves.model.User;
import com.worldtreeinc.leaves.utility.ActivityLauncher;

import java.util.ArrayList;


public class PlannerDashActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView bidList;
    private PlannerDashItemListAdapter listAdapter;
    private ProgressView loader;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoggedInUser();
        setContentView(R.layout.activity_planner_dash);
        initialize();
        new ItemAsyncTask().execute();
    }

    private void checkLoggedInUser() {
        if (!User.isLoggedIn()) {
            Intent getStartedIntent = new Intent(this, GetStartedActivity.class);
            startActivity(getStartedIntent);
        }
    }

    private void initialize() {
        bidList = (ListView) findViewById(R.id.items_list);
        frame = (FrameLayout)findViewById(R.id.frame_loader);
        loader = (ProgressView) findViewById(R.id.loading);
        Button createEventBtn = (Button) findViewById(R.id.create_event_btn);
        createEventBtn.setOnClickListener(this);
        Button manageEventBtn = (Button) findViewById(R.id.manage_events_btn);
        manageEventBtn.setOnClickListener(this);
    }

    private class ItemAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            EventItem item = EventItem.getFirstByUserId();
            listAdapter.clear();
            if (item != null){
                listAdapter.add(item);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listAdapter = new PlannerDashItemListAdapter(PlannerDashActivity.this, new ArrayList<EventItem>());
            loader.start();
        }

        @Override
        protected void onPostExecute(Void result) {
            bidList.setAdapter(listAdapter);
            frame.setVisibility(View.GONE);
            loader.stop();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planner_dash, menu);
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
        }else if(id == R.id.action_logout) {
            Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show();

            User.logout();
            ActivityLauncher.runIntent(this, WelcomeActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Class activitySwitch = null;
        switch (v.getId()) {
            case R.id.create_event_btn:
                activitySwitch = EventActivity.class;
                break;
            case R.id.manage_events_btn:
                activitySwitch = PlannerEventActivity.class;
                break;
        }
        Intent intent = new Intent(PlannerDashActivity.this, activitySwitch);
        startActivity(intent);
    }
}