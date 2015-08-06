package com.worldtreeinc.leaves;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Created by andela on 8/6/15.
 */
public class CreateEventActivityTest extends ActivityInstrumentationTestCase2<CreateEventActivity> {

    CreateEventActivity createEventActivity;
    EditText eventName;

    public CreateEventActivityTest(){super(CreateEventActivity.class);}


    protected void setUp() throws Exception {
        super.setUp();

        /** Getting the reference to the activity containing listview to be tested */
        createEventActivity = getActivity();

        /** Getting the reference to the activity to be tested */
        eventName = (EditText) createEventActivity.findViewById(R.id.create_event_name);



    }
}
