package com.worldtreeinc.leaves;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
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
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

/**
 * Created by andela on 8/11/15.
 */
public class EventForm implements View.OnClickListener, Spinner.OnItemSelectedListener {

    Activity activity;
    // global variables to be used in multiple methods.
    private static int RESULT_LOAD = 1;
    boolean bannerSelected = false;

    String imagePath;
    // form objects
    EditText eventNameEditText;
    Spinner eventCategorySpinner;
    EditText eventDateEditText;
    EditText eventEntryFeeEditText;
    EditText eventVenueEditText;
    EditText eventDescriptionEditText;
    ProgressView progressView;
    ImageView eventBannerImageView;

    // Values from the form
    String eventName;
    String eventCategory = "General";
    String eventEntryFee;
    String eventDate;
    String eventVenue;
    String eventDescription;

    ParseFile file;

    EventUtil eventUtil = new EventUtil();
    Event event = new Event(); // event object

    public EventForm(Activity activity) {
        this.activity = activity;
        initialize();

    }
    private void initialize() {
        // find all UI element by ID
        eventNameEditText = (EditText) activity.findViewById(R.id.create_event_name);
        eventDateEditText = (EditText) activity.findViewById(R.id.create_event_date);
        eventVenueEditText = (EditText) activity.findViewById(R.id.create_event_venue);
        eventEntryFeeEditText = (EditText) activity.findViewById(R.id.create_event_entry_fee);
        eventDescriptionEditText = (EditText) activity.findViewById(R.id.event_description);
        eventBannerImageView = (ImageView) activity.findViewById(R.id.event_banner);
        ImageButton datePicker = (ImageButton) activity.findViewById(R.id.date_picker);
        progressView = (ProgressView) activity.findViewById(R.id.progress_view);
        datePicker.setOnClickListener(this);
        ImageButton bannerUploader = (ImageButton) activity.findViewById(R.id.banner_select_icon);
        bannerUploader.setOnClickListener(this);
        final ImageButton clearBanner = (ImageButton) activity.findViewById(R.id.clear_banner_icon);
        clearBanner.setOnClickListener(this);
        eventCategorySpinner = (Spinner) activity.findViewById(R.id.events_categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.events_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        eventCategorySpinner.setAdapter(adapter);
        eventCategorySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(Spinner spinner, View view, int i, long l) {
        eventCategory = spinner.getSelectedItem().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_picker:
                selectDate(activity, eventDateEditText);
                break;
            case R.id.clear_banner_icon:
                clearEventBanner();
                break;
        }
    }

    public void clearEventBanner() {
        Drawable drawable;
        if (android.os.Build.VERSION.SDK_INT < 21) {
            drawable = activity.getResources().getDrawable(R.drawable.default_image);
        } else {
            drawable = activity.getApplicationContext().getDrawable(R.drawable.default_image);
        }
        eventBannerImageView.setImageDrawable(drawable);
        bannerSelected = false;
    }

    // method to get all form data.
    public void getFormData() {
        eventName = eventNameEditText.getText().toString().trim();
        eventDate = eventDateEditText.getText().toString().trim();
        eventEntryFee = eventEntryFeeEditText.getText().toString().trim();
        eventVenue = eventVenueEditText.getText().toString().trim();
        eventDescription = eventDescriptionEditText.getText().toString().trim();
    }

    public void create() {
        // check for internet connection
        if (NetworkUtil.getConnectivityStatus(activity) == 0) {
            Toast.makeText(activity, "No Internet Connection.", Toast.LENGTH_LONG).show();
            return;
        }
        getFormData();

        // do form validation.
        if (!formValid()) {
            return; // break out of the method
        }

        new ParseImageSelector().execute();
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

    // private async class to handle heavy image loading to parse
    public class ParseImageSelector extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // start the progress view
            progressView.start();
        }
        @Override
        protected Void doInBackground(Void... params) {
            if (bannerSelected) {
                file = eventUtil.getByteArray(imagePath);
            } else {
                // set default banner for event
                Drawable drawable;
                if (android.os.Build.VERSION.SDK_INT < 21) {
                    drawable = activity.getResources().getDrawable(R.drawable.default_image);
                } else {
                    drawable = activity.getApplicationContext().getDrawable(R.drawable.default_image);
                }
                Bitmap bitmap = drawableToBitmap(drawable);
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
                    Toast.makeText(activity.getApplicationContext(), "Event Created.", Toast.LENGTH_LONG).show();
                    // finish activity and move to dashActivity
                    eventUtil.backToDash(activity);
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

    public void cancelEvent() {
        // check if user has entered any data into the form
        getFormData();
        if ( eventName.equals("") &&
                eventEntryFee.equals("") &&
                eventDate.equals("") &&
                eventVenue.equals("") &&
                eventDescription.equals("") &&
                file == null
                ) {
            // close the form and return to the dashboard
            eventUtil.backToDash(activity);
        } else {
            // build up the dialog
            eventUtil.dialog(activity);
        }
    }

    public void setBannerSelected(boolean status) {
        this.bannerSelected = status;
    }

    public void setBannerPath(String path) {
        this.imagePath = path;
    }

    public void selectDate(Context context, EditText eventDateInput) {
        final EditText eventDateEditText = eventDateInput;
        int mYear, mMonth, mDay;
        // Process to get Current Date
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Display Selected date in text box
                eventDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }


    public void setEventBanner(ImageView eventBannerImageView, String imagePath) {
        // create a new banner compressor object
        EventBannerCompressor compressor = new EventBannerCompressor();

        // Set the Image in ImageView after decoding the String
        Bitmap bitmap = compressor.getCompressed(imagePath, 450, 900);
        eventBannerImageView.setImageBitmap(bitmap);
    }



    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        Log.v("Bitmap width - Height :", width + " : " + height);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
