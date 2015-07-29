package com.worldtreeinc.leaves;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class PlannerEventActivity extends ActionBarActivity {

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    private List<UserEvent> userEventList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_event);
        new RemoteDataTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planner_event, menu);
        return true;
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            userEventList = new ArrayList<UserEvent>();
            try {
                // Locate the class table named "event" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Events");
                // Locate the column named "ranknum" in Parse.com and order list
                // by descending
                query.orderByDescending("userId");
                ob = query.find();
                for (ParseObject event : ob) {
                    // Locate images in flag column
                    ParseFile image = (ParseFile) event.get("eventBanner");

                    UserEvent map = new UserEvent();
                    map.setEventDescription((String) event.get("eventDescription"));
                    map.setEventDate((String) event.get("eventDate"));
                    map.setEventCategory((String) event.get("eventCategory"));
                    map.setEventName((String) event.get("eventName"));
                    map.setEventVenue((String) event.get("eventVenue"));
                    map.setEventBanner(image.getUrl());
                    userEventList.add(map);

                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(PlannerEventActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("My event list");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listView);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(PlannerEventActivity.this,
                    userEventList);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
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
