package com.worldtreeinc.leaves.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.utility.EventLoaderTask;
import com.worldtreeinc.leaves.R;

import java.util.List;


public class PlannerEventActivity extends AppCompatActivity {

    String id;
    ListView listview;
    List<Event> event;
    private EventLoaderTask eventLoaderTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_event_list);

        eventLoaderTask = new EventLoaderTask(listview, this, true);
        eventLoaderTask.fetchEvents(true, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planner_event, menu);
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