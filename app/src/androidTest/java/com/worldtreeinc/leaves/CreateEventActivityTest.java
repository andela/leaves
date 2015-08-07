package com.worldtreeinc.leaves;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.Spinner;

/**
 * Created by andela on 8/6/15.
 */
public class CreateEventActivityTest extends ActivityInstrumentationTestCase2<CreateEventActivity> {

    CreateEventActivity createEventActivity;
    EditText eventName;
    ImageView eventNameImage;
    Spinner eventCategorySpinner;
    ImageView eventCategorySpinnerImage;
    EditText entryFee;
    ImageView entryFeeImage;
    EditText createEventDate;
    ImageButton datePickerImage;
    EditText createEventVenue;
    ImageView createEventVenueImage;
    EditText eventDescription;
    ImageView eventDescriptionImage;
    ImageView eventBanner;
    ImageButton eventBannerIcon;
    ImageButton clearBanner;
    ProgressView progressView;
    Button createEventButton;

    public CreateEventActivityTest(){super(CreateEventActivity.class);}


    protected void setUp() throws Exception {
        super.setUp();

        /** Getting the reference to the activity containing listview to be tested */
        createEventActivity = getActivity();

        /** Getting the reference to the activity to be tested */
        eventName = (EditText) createEventActivity.findViewById(R.id.create_event_name);
        eventNameImage = (ImageView) createEventActivity.findViewById(R.id.event_name_image);
        eventCategorySpinner = (Spinner) createEventActivity.findViewById(R.id.events_categories_spinner);
        eventCategorySpinnerImage = (ImageView) createEventActivity.findViewById(R.id.events_categories_spinner_image);
        entryFee = (EditText) createEventActivity.findViewById(R.id.create_event_entry_fee);
        entryFeeImage = (ImageView) createEventActivity.findViewById(R.id.entry_fee_image);
        createEventDate = (EditText) createEventActivity.findViewById(R.id.create_event_date);
        datePickerImage = (ImageButton) createEventActivity.findViewById(R.id.date_picker);
        createEventVenue = (EditText) createEventActivity.findViewById(R.id.create_event_venue);
        createEventVenueImage = (ImageView) createEventActivity.findViewById(R.id.create_event_venue_image);
        eventDescription = (EditText) createEventActivity.findViewById(R.id.event_description);
        eventDescriptionImage = (ImageView) createEventActivity.findViewById(R.id.event_description_image);
        eventBanner = (ImageView) createEventActivity.findViewById(R.id.event_banner);
        eventBannerIcon = (ImageButton) createEventActivity.findViewById(R.id.banner_select_icon);
        clearBanner = (ImageButton) createEventActivity.findViewById(R.id.clear_banner_icon);
        progressView = (ProgressView) createEventActivity.findViewById(R.id.progress_view);
        createEventButton = (Button) createEventActivity.findViewById(R.id.create_event_button);

    }


    public void testPreconditions(){

        assertNotNull("Event Name is Null", eventName);

        assertNotNull("CreateEventActivity is null", createEventActivity);

        assertNotNull("eventNameImage is null", eventNameImage);

        assertNotNull("eventCategorySpinner is null", eventCategorySpinner);

        assertNotNull("eventCategorySpinnerImage is null", eventCategorySpinnerImage);

        assertNotNull("entryFee is null", entryFee);

        assertNotNull("entryFeeImage is null", entryFeeImage);

        assertNotNull("createEventDate is null", createEventDate);

        assertNotNull("datePickerImage is null", datePickerImage);

        assertNotNull("createEventVenue is null", createEventVenue);

        assertNotNull("createEventVenueImage is null", createEventVenueImage);

        assertNotNull("eventDescription is null", eventDescription);

        assertNotNull("eventDescriptionImage is null", eventDescriptionImage);

        assertNotNull("eventBanner is null", eventBanner);

        assertNotNull("eventBannerIcon is null", eventBannerIcon);

        assertNotNull("clearBanner is null", clearBanner);

        assertNotNull("progressView is null", progressView);

        assertNotNull("createEventButton is null", createEventButton);



        final View decorView = createEventActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, createEventButton);

        ViewAsserts.assertOnScreen(decorView, progressView);

        ViewAsserts.assertOnScreen(decorView, clearBanner);

        ViewAsserts.assertOnScreen(decorView, eventBannerIcon);

        ViewAsserts.assertOnScreen(decorView, eventBanner);

        ViewAsserts.assertOnScreen(decorView, eventDescriptionImage);

        ViewAsserts.assertOnScreen(decorView, eventDescription);

        ViewAsserts.assertOnScreen(decorView, createEventVenueImage);

        ViewAsserts.assertOnScreen(decorView, createEventVenue);

        ViewAsserts.assertOnScreen(decorView, datePickerImage);

        ViewAsserts.assertOnScreen(decorView, createEventDate);

        ViewAsserts.assertOnScreen(decorView, entryFeeImage);
        ViewAsserts.assertOnScreen(decorView, entryFee);

        ViewAsserts.assertOnScreen(decorView, eventCategorySpinnerImage);

        ViewAsserts.assertOnScreen(decorView, eventCategorySpinner);

        ViewAsserts.assertOnScreen(decorView, eventNameImage);
        ViewAsserts.assertOnScreen(decorView, eventName);

    }




}
