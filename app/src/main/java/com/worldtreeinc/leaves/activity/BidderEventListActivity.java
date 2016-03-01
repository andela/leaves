package com.worldtreeinc.leaves.activity;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rey.material.widget.Spinner;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.utility.EventLoaderTask;
import com.worldtreeinc.leaves.R;

import java.util.List;

public class BidderEventListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    private EventLoaderTask eventLoaderTask;
    private String selectedCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder_event_list);

        initializeSpinner();
        initializeEventLoaderTask();
    }

    private void initializeEventLoaderTask() {
        eventLoaderTask = new EventLoaderTask(listView, this, false);
        String defaultCategory = getResources().getStringArray(R.array.events_categories)[0];
        selectedCategory = defaultCategory;
        eventLoaderTask.fetchEvents(false, defaultCategory);
    }

    private void initializeSpinner() {
        Spinner eventCategories = (Spinner) findViewById(R.id.bidder_events_categories_spinner);

        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.events_categories, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_style);
        eventCategories.setAdapter(spinnerAdapter);
        eventCategories.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l) {
                selectedCategory = spinner.getSelectedItem().toString();
                refreshList(selectedCategory);
            }
        });

        eventLoaderTask = new EventLoaderTask(listView, this, false);
        String defaultCategory = getResources().getStringArray(R.array.events_categories)[0];
        eventLoaderTask.fetchEvents(false, defaultCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bidder_event_list, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_new_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search Events");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshList(String category) {
        eventLoaderTask.updateEventList(category);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        Thread searchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                eventLoaderTask.runningSearch(newText, selectedCategory);
            }
        });
        searchThread.run();
        return true;
    }
}
