package com.worldtreeinc.leaves.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rey.material.widget.Spinner;
import com.worldtreeinc.leaves.utility.EventLoaderTask;
import com.worldtreeinc.leaves.R;

public class BidderEventListActivity extends AppCompatActivity {

    private ListView listView;
    private EventLoaderTask eventLoaderTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder_event_list);

        Spinner eventCategories = (Spinner) findViewById(R.id.bidder_events_categories_spinner);

        // simple_spinner_dropdown_item
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.events_categories, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_style);
        eventCategories.setAdapter(spinnerAdapter);
        eventCategories.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l) {
                String selectedCategory = spinner.getSelectedItem().toString();
                refreshList(selectedCategory);
            }
        });

        eventLoaderTask = new EventLoaderTask(listView, this, false);
        String defaultCategory = getResources().getStringArray(R.array.events_categories)[0];
        eventLoaderTask.fetchEvents(false, defaultCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bidder_event_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_new_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Events");
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

    private void refreshList(String category) {
        eventLoaderTask.updateEventList(category);
    }
}
