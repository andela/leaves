package com.worldtreeinc.leaves;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.Spinner;

import java.io.ByteArrayOutputStream;

public class CreateEventActivity extends AppCompatActivity  implements Spinner.OnItemSelectedListener, View.OnClickListener{

    // global variables to be used in multiple methods.
    private static int RESULT_LOAD = 1;
    String imagePath;
    ProgressView progressView;
    Event event = new Event(); // event object
    boolean bannerSelected = false;

    // form objects
    EditText eventNameEditText;
    Spinner eventCategorySpinner;
    EditText eventDateEditText;
    EditText eventEntryFeeEditText;
    EditText eventVenueEditText;
    EditText eventDescriptionEditText;
    ImageView eventBannerImageView;

    // Values from the form
    String eventName;
    String eventCategory;
    String eventEntryFee;
    String eventDate;
    String eventVenue;
    String eventDescription;
    ParseFile file;
    EventData eventData = new EventData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);
        initialize();
    }

    private void initialize() {
        // find all UI element by ID
        eventNameEditText = (EditText) findViewById(R.id.create_event_name);
        eventDateEditText = (EditText) findViewById(R.id.create_event_date);
        eventVenueEditText = (EditText) findViewById(R.id.create_event_venue);
        eventEntryFeeEditText = (EditText) findViewById(R.id.create_event_entry_fee);
        eventDescriptionEditText = (EditText) findViewById(R.id.event_description);
        eventBannerImageView = (ImageView) findViewById(R.id.event_banner);
        ImageButton datePicker = (ImageButton) findViewById(R.id.date_picker);
        datePicker.setOnClickListener(this);
        Button createEventButton = (Button) findViewById(R.id.create_event_button);
        createEventButton.setOnClickListener(this);
        ImageButton bannerUploader = (ImageButton) findViewById(R.id.banner_select_icon);
        bannerUploader.setOnClickListener(this);
        final ImageButton clearBanner = (ImageButton) findViewById(R.id.clear_banner_icon);
        clearBanner.setOnClickListener(this);
        eventCategorySpinner = (Spinner) findViewById(R.id.events_categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.events_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        eventCategorySpinner.setAdapter(adapter);
        eventCategorySpinner.setOnItemSelectedListener(this);
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
        if (id == R.id.create_event_cancel) {
            cancelEvent();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        cancelEvent();
    }

    public void cancelEvent() {
        // check if user has entered any data into the form
        getFormData();
        if (
                eventName.equals("") &&
                        eventEntryFee.equals("") &&
                        eventDate.equals("") &&
                        eventVenue.equals("") &&
                        eventDescription.equals("") &&
                        file == null
                ) {
            // close the form and return to the dashboard
            backToDash();
        } else {
            // build up the dialog
            new AlertDialog.Builder(this)
                    .setTitle("Cancel Event")
                    .setMessage("Are you sure you want to cancel event? You will lose all data entered.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            // close the form and return to the dashboard
                            backToDash();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    // method to switch activity
    public void backToDash() {
        Intent intent = new Intent(this, PlannerDashActivity.class);
        startActivity(intent);
        finish();
    }

    // method to handle event creation
    public void createEvent() {

        // check for internet connection
        int internetStatus = NetworkUtil.getConnectivityStatus(this);
        if (internetStatus == 0) {
            Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_LONG).show();
            return;
        }

        getFormData();

        // do form validation.
        if (!formValid()) {
            return; // break out of the method
        }

        // check for spinner value
        if (eventCategory == null) {
            eventCategory = "General";
        }

        // process user image selection
        new createParseImage().execute();
    }

    // private async class to handle heavy image loading to parse
    private class createParseImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // start the progress view
            progressView = (ProgressView) findViewById(R.id.progress_view);
            progressView.start();
        }
        @Override
        protected Void doInBackground(Void... params) {
            if (bannerSelected) {

                file = EventUtil.getByteArray(imagePath);
            } else {
                // set default banner for event
                Drawable drawable;
                if (android.os.Build.VERSION.SDK_INT < 21) {
                    drawable = getResources().getDrawable(R.drawable.default_image);
                } else {
                    drawable = getApplicationContext().getDrawable(R.drawable.default_image);
                }
                Bitmap bitmap = eventData.drawableToBitmap(drawable);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] parseFile = stream.toByteArray();
                file = new ParseFile("banner.jpg", parseFile);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // after all validation has passed, send the image to the server
            sendEventBannerToParse();
        }
    }


    public void sendEventBannerToParse() {
        file.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                // Handle success or failure here ...
                if (e == null) {
                    // save complete event object to parse
                    saveEvent();
                } else {
                    // display error message
                }
            }
        }, new ProgressCallback() {
            public void done(Integer percentDone) {
                // Update your progress spinner here. percentDone will be between 0 and 100.
            }
        });
    }

    public void saveEvent() {
        // assign values from form to event object
        compileEventData();

        // save the event in the parse database.
        event.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // handle success or failure here
                if (e == null) {
                    // stop the progress view
                    progressView.stop();
                    // show a toast
                    Toast.makeText(getApplicationContext(), "Event Created.", Toast.LENGTH_LONG).show();
                    // finish activity and move to dashActivity
                    backToDash();
                }
            }
        });
    }

    public void compileEventData() {
        event.setEventName(eventName);
        event.setEventCategory(eventCategory);
        event.setEventEntryFee(Integer.parseInt(eventEntryFee));
        event.setEventDate(eventDate);
        event.setEventVenue(eventVenue);
        event.setEventDescription(eventDescription);
        event.setEventBanner(file);
        // get current user from localStore
        ParseUser currentUser = ParseUser.getCurrentUser();
        event.setUserId(currentUser.getObjectId());
    }

    // method to perform form validation
    public boolean formValid() {

        boolean validate = true;

        if (eventName.equals("")) {
            eventNameEditText.setError("Event name is required!");
            validate = false;
        }
        if (eventEntryFee.equals("")) {
            eventEntryFeeEditText.setError("Event entry fee is required. Min: 0");
            validate = false;
        }
        if (eventDate.equals("")) {
            eventDateEditText.setError("Event date is required!");
            validate = false;
        }
        if (eventVenue.equals("")) {
            eventVenueEditText.setError("Event venue is required!");
            validate = false;
        }
        if (eventDescription.equals("")) {
            eventDescriptionEditText.setError("Event description is required!");
            validate = false;
        }

        return validate;
    }

    // method to get all form data.
    public void getFormData() {
        eventName = eventNameEditText.getText().toString().trim();
        eventDate = eventDateEditText.getText().toString().trim();
        eventEntryFee = eventEntryFeeEditText.getText().toString().trim();
        eventVenue = eventVenueEditText.getText().toString().trim();
        eventDescription = eventDescriptionEditText.getText().toString().trim();
    }


    // method to open gallery
    public void openGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD);
    }

    // method to be invoked when a picture is selected in the gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // process the selected image
        //processSelectedImage(requestCode, resultCode, data);
        EventUtil.processSelectedImage(this, requestCode, resultCode, data, RESULT_LOAD, RESULT_OK, eventBannerImageView);
        bannerSelected = EventUtil.getBannerSelectedStatus();
        imagePath = EventUtil.getBannerPath();
    }

    public void clearEventBanner() {
        Drawable drawable;
        if (android.os.Build.VERSION.SDK_INT < 21) {
            drawable = getResources().getDrawable(R.drawable.default_image);
        } else {
            drawable = getApplicationContext().getDrawable(R.drawable.default_image);
        }
        eventBannerImageView.setImageDrawable(drawable);
        bannerSelected = false;
    }

    @Override
    public void onItemSelected(Spinner spinner, View view, int i, long l) {
        eventCategory = spinner.getSelectedItem().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_picker:
                eventData.selectDate(this, eventDateEditText);
                break;
            case R.id.clear_banner_icon:
                clearEventBanner();
                break;
            case R.id.banner_select_icon:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        openGallery();
                    }
                }).start();
                break;
            case R.id.create_event_button:
                createEvent();
                break;

        }
    }
}