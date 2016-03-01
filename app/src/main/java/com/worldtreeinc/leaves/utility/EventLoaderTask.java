package com.worldtreeinc.leaves.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.activity.BidderEventListActivity;
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
        mProgressDialog.setTitle(category + activity.getString(R.string.bidder_event_list_dialog));
        mProgressDialog.setMessage(activity.getString(R.string.event_list_progress_loading));
        mProgressDialog.setIndeterminate(false);
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
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Event event = eventsListAdapter.getCurrentEvent(position);
            Intent intent = new Intent(activity.getApplicationContext(), EventDetailsActivity.class);
            intent.putExtra(activity.getString(R.string.object_id_reference), event.getObjectId());
            if (isPlanner) {
                intent.putExtra(activity.getString(R.string.is_planner_reference), true);
            } else {
                intent.putExtra(activity.getString(R.string.is_planner_reference), false);
            }
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
            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setMessage(activity.getString(R.string.event_list_progress_loading));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            listView = (ListView) activity.findViewById(R.id.listView);
            listView.setAdapter(eventsListAdapter);
            listView.setOnItemClickListener(mMessageClickedHandler);
            mProgressDialog.dismiss();
        }
    }

    public void runningSearch(final String query, final String category) {
        final List<Event> events = Event.getAll(objectReferrence, category);
        final List<Event> matchedEvents = new ArrayList<Event>();
        for (Event event: events) {
            String name = event.getField("eventName").toLowerCase();
            if(name.contains(query.toLowerCase())) {
                matchedEvents.add(event);
            }
        }
        eventsListAdapter = new EventsListAdapter(activity,matchedEvents, isPlanner);
        //listView = (ListView) activity.findViewById(R.id.listView);
        listView.setAdapter(eventsListAdapter);
        eventsListAdapter.notifyDataSetChanged();
    }
}
