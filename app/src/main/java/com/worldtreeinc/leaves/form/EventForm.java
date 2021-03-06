package com.worldtreeinc.leaves.form;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.Spinner;
import com.worldtreeinc.leaves.helper.LeavesNotification;
import com.worldtreeinc.leaves.utility.DialogBox;
import com.worldtreeinc.leaves.utility.NetworkUtil;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.model.Banner;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.activity.EntryFeeWatcher;
import java.util.Calendar;


/**
 * Created by andela on 8/11/15.
 */
public class EventForm implements View.OnClickListener, Spinner.OnItemSelectedListener {

    Activity activity;
    // global variables to be used in multiple methods.
    private static int RESULT_LOAD = 1;

    public boolean bannerSelected = false;
    public boolean updateBanner = false;

    public String imagePath;
    // form objects
    private EditText eventNameEditText;
    private Spinner eventCategorySpinner;
    private EditText eventDateEditText;
    private EditText eventEntryFeeEditText;
    private EditText eventVenueEditText;
    private EditText eventDescriptionEditText;
    private ProgressView progressView;
    public ImageView eventBannerImageView;

    // Values from the form
    private String eventName;
    private String eventCategory = "General";
    private String eventEntryFee;
    private String eventDate;
    private String eventVenue;
    private String eventDescription;
    private String eventId;
    private ArrayAdapter<CharSequence> adapter;

    private ParseFile file;

    private DialogBox eventFormCancelDialog = new DialogBox();
    private Event event = new Event(); // event object
    private Banner banner = new Banner();

    public EventForm(Activity activity) {
        this.activity = activity;
        initialize();
    }

    private void initialize() {
        // find all UI element by ID
        eventNameEditText = (EditText) activity.findViewById(R.id.event_name);
        eventDateEditText = (EditText) activity.findViewById(R.id.event_date);
        eventVenueEditText = (EditText) activity.findViewById(R.id.event_venue);
        eventEntryFeeEditText = (EditText) activity.findViewById(R.id.event_entry_fee);
        eventEntryFeeEditText.setFilters(new InputFilter[] {new EntryFeeWatcher(2)});
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
        adapter = ArrayAdapter.createFromResource(activity,
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
                banner.clear(activity, eventBannerImageView);
                this.bannerSelected = false;
                this.updateBanner = false;
                break;
        }
    }

    // method to get all form data.
    public void getData() {
        eventName = eventNameEditText.getText().toString().trim();
        eventDate = eventDateEditText.getText().toString().trim();
        eventEntryFee = eventEntryFeeEditText.getText().toString().trim();
        eventVenue = eventVenueEditText.getText().toString().trim();
        eventDescription = eventDescriptionEditText.getText().toString().trim();
    }

    public void setData(String eventId) {
        Event eventObject = Event.getOne(eventId);

        file = (ParseFile) eventObject.get("eventBanner");
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                eventBannerImageView.setImageBitmap(bmp);
            }
        });

        updateBanner = true;
        bannerSelected = true;

        eventNameEditText.setText(eventObject.getString("eventName"));
        eventDateEditText.setText(eventObject.getString("eventDate"));
        eventEntryFeeEditText.setText(eventObject.getNumber("entryFee").toString());
        eventVenueEditText.setText(eventObject.getString("eventVenue"));
        eventDescriptionEditText.setText(eventObject.getString("eventDescription"));
        eventCategory = eventObject.getString("eventCategory");
        eventCategorySpinner.setSelection(adapter.getPosition(eventCategory));
    }

    public void uploadData() {
        // check for internet connection
        if (!NetworkUtil.getConnectivityStatus(activity)) {
            Toast.makeText(activity, "No Internet Connection.", Toast.LENGTH_LONG).show();
            return;
        }

        getData();

        // do form validation.
        if (!isValid()) {
            return; // break out of the method
        }

        new ParseImageSelector().execute();
    }

    public void create() {
        uploadData();
    }

    public void update(String eventId) {
        this.eventId = eventId;
        uploadData();
    }

    // method to perform form validation
    public boolean isValid() {

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
            String bannerName = "banner.jpg";
            if (bannerSelected) {
                if (!updateBanner) {
                    file = banner.getImageFromGallery(imagePath, bannerName);
                }
            } else {
                // set default banner for event
                Drawable drawable;
                if (android.os.Build.VERSION.SDK_INT < 21) {
                    drawable = activity.getResources().getDrawable(R.drawable.default_image);
                } else {
                    drawable = activity.getApplicationContext().getDrawable(R.drawable.default_image);
                }
                Bitmap bitmap = banner.drawableToBitmap(drawable);

                file = banner.getImageFromBitmap(bitmap, bannerName);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // after all validation has passed, send the image to the server
            sendBanner();
        }
    }

    public void sendBanner() {
        file.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                // Handle success or failure here ...
                if (e == null) {
                    // save complete event object to parse
                    save();
                } else {
                    // display error message
                    e.printStackTrace();
                }
            }
        });
    }

    public void save() {
        // check if it is new event or event for update
        if (eventId == null) {
            // assign values from form to event object
            compileEventData();
            saveToDatabase(activity.getString(R.string.create_event_form_toast));
        }
        else {
            event = Event.getOne(eventId);
            compileEventData();
            saveToDatabase(activity.getString(R.string.update_event_form_toast));
        }
    }

    public void saveToDatabase(String feedbackText) {
        final String text = feedbackText;
        AsyncTask<Void, Void, Void> itemAsync = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    event.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // stop the progress view
                progressView.stop();
                // show a toast
                Toast.makeText(activity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
                // finish context and move to plannerEventListActivity
                eventFormCancelDialog.backToEventList(activity);
                LeavesNotification.subscribePlannerToEventChannel(event);
            }
        };
        itemAsync.execute();
    }

    public void compileEventData() {
        event.setName(eventName);
        event.setCategory(eventCategory);
        event.setEntryFee(Double.parseDouble(eventEntryFee));
        event.setDate(eventDate);
        event.setVenue(eventVenue);
        event.setDescription(eventDescription);
        event.setBanner(file);
        event.setEntries();
        // get current user from localStore
        ParseUser currentUser = ParseUser.getCurrentUser();
        event.setUserId(currentUser.getObjectId());
    }

    public void cancelEvent() {
        // check if user has entered any data into the form
        getData();
        if ( eventName.equals("") &&
                eventEntryFee.equals("") &&
                eventDate.equals("") &&
                eventVenue.equals("") &&
                eventDescription.equals("") &&
                file == null
                ) {
            // close the form and return to the dashboard
            eventFormCancelDialog.backToEventList(activity);
        } else {
            // build up the dialog
            eventFormCancelDialog.dialog(activity, activity.getString(R.string.cancel_event_title), activity.getString(R.string.cancel_event_message), new DialogBox.CallBack() {
                @Override
                public void onFinished() {
                    eventFormCancelDialog.backToEventList(activity);
                }
            });
        }
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

}
