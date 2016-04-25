package com.worldtreeinc.leaves.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseUser;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.adapter.MyBidsAdapter;
import com.worldtreeinc.leaves.adapter.ReportAdapter;
import com.worldtreeinc.leaves.helper.MyToolbar;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.model.User;

import java.util.List;

public class ViewReportActivity extends AppCompatActivity {
    private List<Event> allEvent;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        MyToolbar.setToolbar(this);
        new EventsAsyncTask().execute();
    }

    private void getEventsCreated(){
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        allEvent = Event.getAll("userId", currentUserId );
    }

    private class EventsAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getEventsCreated();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ViewReportActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ReportAdapter eventAdapter = new ReportAdapter(ViewReportActivity.this, allEvent);
            ListView listView = (ListView) findViewById(R.id.view_report_listView);
            listView.setAdapter(eventAdapter);
            mProgressDialog.dismiss();
        }
    }
}
