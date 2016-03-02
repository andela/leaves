package com.worldtreeinc.leaves.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.utility.EventLoaderTask;


public class PlannerEventActivity extends AppCompatActivity {
    String id;
    private ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_event_list);

        EventLoaderTask eventLoaderTask = new EventLoaderTask(listview, this, true);
        eventLoaderTask.fetchEvents(true, null);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_planner_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}