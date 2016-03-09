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

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import com.rey.material.widget.ProgressView;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.adapter.PlannerDashItemListAdapter;
import com.worldtreeinc.leaves.model.EventItem;
import com.worldtreeinc.leaves.model.User;
import com.worldtreeinc.leaves.utility.ActivityLauncher;

import java.util.ArrayList;


public class PlannerDashActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ListView bidList;
    private PlannerDashItemListAdapter listAdapter;
    private ProgressView loader;
    private FrameLayout frame;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoggedInUser();
        setContentView(R.layout.activity_planner_dash);
        setUpNavigationDrawer();
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
        mToolbar = (Toolbar) findViewById(R.id.planner_toolbar);
        bidList = (ListView) findViewById(R.id.items_list);
        frame = (FrameLayout)findViewById(R.id.frame_loader);
        loader = (ProgressView) findViewById(R.id.loading);
        Button createEventBtn = (Button) findViewById(R.id.create_event_btn);
        createEventBtn.setOnClickListener(this);
        Button manageEventBtn = (Button) findViewById(R.id.manage_events_btn);
        manageEventBtn.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_bidder) {
            ActivityLauncher.runIntent(this, BidderDashActivity.class);
        } else if (id == R.id.nav_logout) {
            User.logout();
            ActivityLauncher.runIntent(this, WelcomeActivity.class);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        getMenuInflater().inflate(R.menu.menu_planner_dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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

    private void setUpNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}