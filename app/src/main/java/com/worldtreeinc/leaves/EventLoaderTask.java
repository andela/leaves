package com.worldtreeinc.leaves;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseUser;

import java.util.List;

/**
 * Created by kamiye on 9/4/15.
 */
public class EventLoaderTask {

    private Activity activity;
    private ListView listView;
    private EventsListAdapter eventsListAdapter;
    private ProgressDialog mProgressDialog;
    private String objectReferrence;
    private String column;


    public EventLoaderTask(ListView listView, Activity activity, EventsListAdapter eventsListAdapter) {
        this.listView = listView;
        this.activity = activity;
        this.eventsListAdapter = eventsListAdapter;
    }

    public void fetchEvents(boolean flag, String category) {
        if (flag) {
            objectReferrence = "userId";
            column = ParseUser.getCurrentUser().getObjectId();
        }
        else {
            if (category != null) {
                objectReferrence = "eventCategory";
                column = category;
            }
        }
        new EventAsyncTask().execute();
    }

    public void updateEventList(final String category) {
        mProgressDialog = new ProgressDialog(activity);
        // Set progressdialog title
        mProgressDialog.setTitle("My event list");
        // Set progressdialog message
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
        eventsListAdapter.clear();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Event> events = Event.getAll(objectReferrence, category);
                eventsListAdapter.addAll(events);
                eventsListAdapter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            }
        });

    }

    private ListView.OnItemClickListener mMessageClickedHandler = new ListView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            Log.e("Clicked: ", "I just got clicked!!!");
            String objectId = Event.getAll(objectReferrence, column).get(position).getObjectId();
            Intent intent = new Intent(activity.getApplicationContext(), EventDetailsActivity.class);
            intent.putExtra("OBJECT_ID", objectId);
            activity.startActivity(intent);
        }
    };

    private class EventAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            eventsListAdapter.addAll(Event.getAll(objectReferrence, column));
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(activity);
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
            listView = (ListView) activity.findViewById(R.id.listView);
            // Binds the Adapter to the ListView
            listView.setAdapter(eventsListAdapter);
            listView.setOnItemClickListener(mMessageClickedHandler);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
