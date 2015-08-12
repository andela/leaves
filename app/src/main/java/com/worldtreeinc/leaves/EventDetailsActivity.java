package com.worldtreeinc.leaves;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    // declare class variables
    private ParseItemsAdapter m_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // get event Id from extras
        String eventId = getIntent().getExtras().getString("OBJECT_ID");

        // create new ParseImageLoader passing in banner to it and eventId
        // and call setImage() method on it
        ParseEventLoader objectLoader = new ParseEventLoader(this, "Events", eventId);

        // set details of the events fetched from parse
        objectLoader.setEventDetails();

        // check internet access
        boolean connecting = checkOnlineState();
        TextView error = (TextView) findViewById(R.id.no_internet_error);
        if (!connecting) {
            error.setText("No Internet Connection");
        }
        else {
            // instantiate m_adapter and pass in Event ID
            m_adapter = new ParseItemsAdapter(this, eventId);
            m_adapter.notifyDataSetChanged();
            if (m_adapter.getCount() == 0) {
                error.setText("No Items to Display");
            }
            // check if there are no items and display message as necessary
            ListView listView = (ListView) findViewById(R.id.items_list);
            listView.setAdapter(m_adapter);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
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

    public boolean checkOnlineState() {
        ConnectivityManager CManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NInfo = CManager.getActiveNetworkInfo();
        return (NInfo != null && NInfo.isConnectedOrConnecting());
    }
}