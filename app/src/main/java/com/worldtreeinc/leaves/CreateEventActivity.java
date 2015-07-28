package com.worldtreeinc.leaves;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import java.util.Calendar;


public class CreateEventActivity extends AppCompatActivity {

    private int mYear, mMonth, mDay;

    // global variables to be used in multiple methods.
    private static int RESULT_LOAD = 1;
    String toastErrorMessage;
    ProgressView progressView;
    Event event = new Event(); // event object

    // form objects
    EditText eventNameEditText;
    Spinner eventCategorySpinner;
    EditText eventDateEditText;
    EditText eventVenueEditText;
    EditText eventDescriptionEditText;
    ImageView eventBannerImageView;

    // Values from the form
    String eventName;
    String eventCategory;
    String eventDate;
    String eventVenue;
    String eventDescription;
    ParseFile file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);

        // find all UI element by ID
        eventNameEditText = (EditText) findViewById(R.id.create_event_name);
        eventDateEditText = (EditText) findViewById(R.id.create_event_date);
        eventVenueEditText = (EditText) findViewById(R.id.create_event_venue);
        eventDescriptionEditText = (EditText) findViewById(R.id.event_description);
        eventBannerImageView = (ImageView) findViewById(R.id.event_banner);

        // populate the spinner with data from a string resource
        populateCategorySpinner();

        // create event onItemSelectedListener for the spinner
        eventCategorySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l) {
                eventCategory = eventCategorySpinner.getSelectedItem().toString();
            }
        });

        // create onClick listener for the date picker
        ImageButton datePicker = (ImageButton) findViewById(R.id.date_picker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        // create onClick listener for image uploader
        ImageButton bannerUploader = (ImageButton) findViewById(R.id.banner_select_icon);
        bannerUploader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // create onClick listener for the createEvent button
        Button createEventButton = (Button) findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });
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
        if (true) {
            // if fields are empty, change activity to the planner dashboard
            backToDash();
        } else {
            // alert user of data loss if activity is switched
            // and ask for confirmation
        }
    }

    // method to handle event creation
    public void createEvent() {

        // clear toast message
        toastErrorMessage = "";

        getFormData();

        // do form validation.
        if (!formValid()) {
            // show toast
            Toast.makeText(this, toastErrorMessage, Toast.LENGTH_LONG).show();
            return; // break out of the method
        }

        // start the progress view
        progressView = (ProgressView) findViewById(R.id.progress_view);
        progressView.start();


        // after all validation has passed, send the image to the server
        file.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                // Handle success or failure here ...
                if (e == null) {
                    buildEventObject();
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

    public void buildEventObject() {
        event.setEventName(eventName);
        event.setEventCategory(eventCategory);
        event.setEventDate(eventDate);
        event.setEventVenue(eventVenue);
        event.setEventDescription(eventDescription);
        event.setEventBanner(file);
        // get current user from localStore
        ParseUser currentUser = ParseUser.getCurrentUser();
        event.setUserId(currentUser.getObjectId());

        // save the event in the parse database.
        event.saveInBackground();

        // stop the progress view
        progressView.stop();

        // show a toast
        Toast.makeText(this, "Event Created.", Toast.LENGTH_LONG).show();

        // finish activity and move to dashActivity
        Intent intent = new Intent(this, PlannerDashActivity.class);
        startActivity(intent);
        finish();
    }

    // method to perform form validation
    public boolean formValid() {

        if (eventName.equals("")) {
            toastErrorMessage = "Event name must be provided.";
            return false;
        }
        if (eventCategory == null || eventCategory.equals("Select Category")) {
            toastErrorMessage = "Event Category must be provided.";
            return false;
        }
        if (eventDate.equals("")) {
            toastErrorMessage = "Event Date must be provided.";
            return false;
        }
        if (eventVenue.equals("")) {
            toastErrorMessage = "Event Venue must be provided.";
            return false;
        }
        if (eventDescription.equals("")) {
            toastErrorMessage = "Event Description must be provided.";
            return false;
        }
        if (file == null) {
            toastErrorMessage = "Please select an event banner.";
            return false;
        }

        return true;
    }

    // method to get all form data.
    public void getFormData() {
        eventName = eventNameEditText.getText().toString();
        eventDate = eventDateEditText.getText().toString();
        eventVenue = eventVenueEditText.getText().toString();
        eventDescription = eventDescriptionEditText.getText().toString();
    }


    // method to switch activity
    public void backToDash() {
        Intent intent = new Intent(this, BidderDashActivity.class);
        startActivity(intent);
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
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String img_Decodable_Str = cursor.getString(columnIndex);
                cursor.close();


                ImageView imgView = (ImageView) findViewById(R.id.event_banner);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(img_Decodable_Str));

                // call method to get byte array from selected image file
                getByteArray(img_Decodable_Str);

            } else {
                Toast.makeText(this, "Pick an image first",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please select an image!", Toast.LENGTH_LONG)
                    .show();
        }

    }

    // method to get byte array of selected image
    public void getByteArray(String filePath) {
        // prepare the image to be sent to parse server
        Bitmap bmp = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] parseFile = stream.toByteArray();
        file = new ParseFile("banner.jpg", parseFile);
    }


    // method to populate spinner
    public void populateCategorySpinner() {

        eventCategorySpinner = (Spinner) findViewById(R.id.events_categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.events_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        eventCategorySpinner.setAdapter(adapter);

    }


    /*
        Date picker section
     */
    public void selectDate() {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Display Selected date in text box
                eventDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }
}