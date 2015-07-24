package com.worldtreeinc.leaves;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import com.rey.material.widget.Spinner;

import java.util.Calendar;


public class CreateEventActivity extends AppCompatActivity {

    EditText eventDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);

        // populate the spinner with data from a string resource
        populateCategorySpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
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

    @Override
    public void onBackPressed() {
        // do some checks on user inputs before any action
        if (emptyFields()) {
            // if fields are empty, change activity to the planner dashboard
            backToDash();
        } else {
            // alert user of data loss if activity is switched
            // and ask for confirmation
        }
    }

    // method to populate spinner
    public void populateCategorySpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.events_categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.events_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    /*
        method to run when back button or cancel button is pressed
        to check if user has entered any data into the form and to
        warn user that all data will be lost
    */
    public boolean emptyFields() {

        return true;
    }

    // method to switch activity
    public void backToDash() {
        Intent intent = new Intent(this, BidderDashActivity.class);
        startActivity(intent);
    }


    /*
        Date picker section
     */
    public void selectDate(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }
    public void populateSetDate(int year, int month, int day) {
        eventDate = (EditText)findViewById(R.id.create_event_date);
        eventDate.setText(month + "/" + day + "/" + year);
    }
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            populateSetDate(year, month + 1, day);
        }
    }
}
