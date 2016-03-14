package com.worldtreeinc.leaves.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.adapter.MyBidsAdapter;
import com.worldtreeinc.leaves.helper.MyToolbar;
import com.worldtreeinc.leaves.model.EventItem;
import com.worldtreeinc.leaves.model.User;

import java.util.List;

public class MyBidActivity extends AppCompatActivity {
    private List<EventItem> items;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyToolbar.setToolbar(this);
        new ItemAsyncTask().execute();
    }

    private void getItems() {
        List<String> itemsBiddedOn = User.getItemsBiddedOn();
        items = EventItem.getItemsBiddedOn(itemsBiddedOn);
    }

    private class ItemAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getItems();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MyBidActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            MyBidsAdapter bidsAdapter = new MyBidsAdapter(MyBidActivity.this, items);
            ListView listView = (ListView) findViewById(R.id.my_bid_listView);
            listView.setAdapter(bidsAdapter);
            mProgressDialog.dismiss();
        }
    }

}
