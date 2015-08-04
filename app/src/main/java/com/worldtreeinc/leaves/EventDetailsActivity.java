package com.worldtreeinc.leaves;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    // declare class variables
    private ParseItemsAdapter m_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);


        // create new ParseImageLoader passing in banner to it and eventId
        // and call setImage() method on it
        ParseObjectLoader objectLoader = new ParseObjectLoader(this, "Events", "a5NzJWEExy");
        objectLoader.setImage();
        // call set texts to set details of the events fetched from parse
        objectLoader.setEventDetails();

        // check internet access
        boolean connecting = checkOnlineState();
        TextView error = (TextView) findViewById(R.id.no_internet_error);
        if (!connecting) {
            error.setText("No Internet Connection");
        }
        else {
            // instantiate m_adapter and pass in object ID { Event ID }
            m_adapter = new ParseItemsAdapter(this, "zNQS9XB8G5");

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
        if (NInfo != null && NInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
