package com.worldtreeinc.leaves;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class PlannerEventActivity extends ActionBarActivity {

    String id;
    ListView listview;
    List<Event> event;
    ProgressDialog mProgressDialog;
    PlannerEventAdapter adapter;
    String currentUserId = ParseUser.getCurrentUser().getObjectId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_event);
        adapter = new PlannerEventAdapter(this,
                new ArrayList<Event>());
        new EventAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planner_event, menu);
        return true;
    }

    private ListView.OnItemClickListener mMessageClickedHandler = new ListView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            String objectId = new Event().getAll(currentUserId).get(position).getObjectId();
            Intent intent = new Intent(getApplicationContext(), EventDetailsActivity.class);
            intent.putExtra("OBJECT_ID", objectId);
            startActivity(intent);
        }
    };

    private class EventAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            adapter.addAll(Event.getAll(currentUserId));
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
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(mMessageClickedHandler);
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