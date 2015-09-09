package com.worldtreeinc.leaves;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;


public class BidderDashActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private Button browseEvent;
    private ListView eventList;
    private EventsListAdapter listAdapter;
    private ProgressView loader;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder_dash);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Dashboard");
        initialize();
        new ItemAsyncTask().execute();
    }

    private void initialize(){
        browseEvent = (Button) findViewById(R.id.browse_event_btn);
        browseEvent.setOnClickListener(this);
        eventList = (ListView) findViewById(R.id.event_list);
        frame = (FrameLayout)findViewById(R.id.frame_loader);
        loader = (ProgressView) this.findViewById(R.id.loading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bidder_dash, menu);
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

    private class ItemAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            List event = Event.getAll1();
            listAdapter.clear();
            listAdapter.addAll(event);
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
                Log.v("TAG", "this browse button has been called");
                Intent bidderEvent = new Intent(this, BidderEventListActivity.class);
                startActivity(bidderEvent);
                break;
        }
    }

}
