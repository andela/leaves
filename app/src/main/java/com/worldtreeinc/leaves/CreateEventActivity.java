package com.worldtreeinc.leaves;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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


public class CreateEventActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{


    // global variables to be used in multiple methods.
    private static int RESULT_LOAD = 1;
    String imagePath;
    ProgressView progressView;
    Event event = new Event(); // event object

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
    EventDataClass eventDataClass = new EventDataClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);
        initialize();

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
                EventDataClass eventDataClass = new EventDataClass(CreateEventActivity.this, eventDateEditText);
                eventDataClass.selectDate();
            }
        });

        // create onClick listener for image uploader
        ImageButton bannerUploader = (ImageButton) findViewById(R.id.banner_select_icon);
        bannerUploader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        openGallery();
                    }
                }).start();
            }
        });

        // create onClick listener for image uploader
        final ImageButton clearBanner = (ImageButton) findViewById(R.id.clear_banner_icon);
        clearBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable;
                if (android.os.Build.VERSION.SDK_INT < 21) {
                    drawable = getResources().getDrawable(R.drawable.default_image);
                } else {
                    drawable = getApplicationContext().getDrawable(R.drawable.default_image);
                }
                eventBannerImageView.setImageDrawable(drawable);
                file = null;
                imagePath = null;
            }
        });

        // create onClick listener for the createEvent button
        Button createEventButton = (Button) findViewById(R.id.create_event_button);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });
    }

    private void initialize() {
        // find all UI element by ID
        eventNameEditText = (EditText) findViewById(R.id.create_event_name);
        eventDateEditText = (EditText) findViewById(R.id.create_event_date);
        eventVenueEditText = (EditText) findViewById(R.id.create_event_venue);
        eventEntryFeeEditText = (EditText) findViewById(R.id.create_event_entry_fee);
        eventDescriptionEditText = (EditText) findViewById(R.id.event_description);
        eventBannerImageView = (ImageView) findViewById(R.id.event_banner);
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

        getFormData();

        // do form validation.
        if (!formValid()) {
            return; // break out of the method
        }

        // check for spinner value
        if (eventCategory == null) {
            eventCategory = "General";
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (imagePath != null) {
                    getByteArray(imagePath);
                }
            }
        }).start();

        // check if user did not select an event banner.
        if (file == null) {
            // set default banner for event
            Drawable drawable;
            if (android.os.Build.VERSION.SDK_INT < 21) {
                drawable = getResources().getDrawable(R.drawable.default_image);
            } else {
                drawable = this.getDrawable(R.drawable.default_image);
            }
            Bitmap bitmap = eventDataClass.drawableToBitmap(drawable);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] parseFile = stream.toByteArray();
            file = new ParseFile("banner.jpg", parseFile);
        }

        // start the progress view
        progressView = (ProgressView) findViewById(R.id.progress_view);
        progressView.start();


        // after all validation has passed, send the image to the server
        file.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                // Handle success or failure here ...
                if (e == null) {
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


        eventCategorySpinner = (Spinner) findViewById(R.id.events_categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.events_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        eventCategorySpinner.setAdapter(adapter);
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
                imagePath = cursor.getString(columnIndex);
                cursor.close();

                // set the imageView to the selected image
                eventDataClass.setEventBanner(eventBannerImageView, imagePath);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please select an image!", Toast.LENGTH_LONG)
                    .show();
        }
    }



    // method to get byte array of selected image
    public void getByteArray(String filePath) {
        // prepare the image to be sent to parse server
        EventBannerCompressor compressor = new EventBannerCompressor();
        Bitmap bmp = compressor.getCompressed(filePath, 450, 900);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] parseFile = stream.toByteArray();
        file = new ParseFile("banner.jpg", parseFile);
        // clear bitmap
        bmp = null;
    }

    // method to populate spinner


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (getTaskId()) {
            case R.id.events_categories_spinner :
                eventCategory = eventCategorySpinner.getSelectedItem().toString();
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}