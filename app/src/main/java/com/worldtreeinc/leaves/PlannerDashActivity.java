package com.worldtreeinc.leaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;


public class PlannerDashActivity extends AppCompatActivity {

    private ListView bidList;
    private BidListAdapter listAdapter;
    private ProgressView loader;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if user is logged in
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            //redirect to get started page
            Intent getStartedIntent = new Intent(this, GetStartedActivity.class);
            startActivity(getStartedIntent);
        }

        setContentView(R.layout.activity_planner_dash);

        //set adapter to list view
        bidList = (ListView) findViewById(R.id.items_list);
        listAdapter = new BidListAdapter(this, new ArrayList<BidModel>());
        bidList.setAdapter(listAdapter);
        frame = (FrameLayout)findViewById(R.id.frame_loader);
        loader = (ProgressView) this.findViewById(R.id.loading);

        updateData();

        Button createEventBtn = (Button) findViewById(R.id.create_event_btn);
        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirect to bidder dashboard
                Intent createEvent = new Intent(PlannerDashActivity.this, CreateEventActivity.class);
                startActivity(createEvent);
            }
        });
        Button manageEventBtn = (Button) findViewById(R.id.manage_events_btn);
        manageEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirect to bidder dashboard
                Intent manageEvent = new Intent(PlannerDashActivity.this, PlannerEventActivity.class);
                startActivity(manageEvent);
            }
        });

    }

    public void updateData(){
        loader.start();
        ParseQuery<BidModel> query = ParseQuery.getQuery(BidModel.class);
        query.findInBackground(new FindCallback<BidModel>() {
            @Override
            public void done(List<BidModel> bidObject, ParseException e) {
                if (e == null) {
                    listAdapter.clear();
                    listAdapter.addAll(bidObject);
                    frame.setVisibility(View.GONE);
                    loader.stop();
                }
                else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
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
        }

        return super.onOptionsItemSelected(item);
    }
}
