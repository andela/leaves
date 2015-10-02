package com.worldtreeinc.leaves.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseUser;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.activity.EventDetailsActivity;
import com.worldtreeinc.leaves.adapter.EventsListAdapter;
import com.worldtreeinc.leaves.model.Event;

import java.util.ArrayList;
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
    private boolean isPlanner;

    public EventLoaderTask(ListView listView, Activity activity, boolean isPlanner) {
        this.listView = listView;
        this.activity = activity;
        eventsListAdapter = new EventsListAdapter(activity, new ArrayList<Event>(), isPlanner);
        this.isPlanner = isPlanner;
    }

    public void fetchEvents(boolean isPlanner, String category) {
        if (isPlanner) {
            objectReferrence = "userId";
            column = ParseUser.getCurrentUser().getObjectId();
        } else {
            objectReferrence = "eventCategory";
            column = category;
        }
        new EventAsyncTask().execute();
    }

    public void updateEventList(final String category) {
        mProgressDialog = new ProgressDialog(activity);
        // Set progressdialog title
        mProgressDialog.setTitle(category+activity.getString(R.string.bidder_event_list_dialog));
        // Set progressdialog message
        mProgressDialog.setMessage(activity.getString(R.string.event_list_progress_loading));
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
        eventsListAdapter.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Event> events = Event.getAll(objectReferrence, category);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        eventsListAdapter.addAll(events);
                        eventsListAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                    }
                });
            }
        }).start();

    }


    private ListView.OnItemClickListener mMessageClickedHandler = new ListView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            Event event = eventsListAdapter.getCurrentEvent(position);
            Intent intent = new Intent(activity.getApplicationContext(), EventDetailsActivity.class);
            intent.putExtra("OBJECT_ID", event.getObjectId());
            if (isPlanner) { intent.putExtra("IS_PLANNER", true); }
            else { intent.putExtra("IS_PLANNER", false); }
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
            // Set progressdialog message
            mProgressDialog.setMessage(activity.getString(R.string.event_list_progress_loading));
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
