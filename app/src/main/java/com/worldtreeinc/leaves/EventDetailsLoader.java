package com.worldtreeinc.leaves;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

/**
 * Created by kamiye on 7/30/15.
 */
public class EventDetailsLoader {

    // set class properties
    private String eventId;
    private Activity eventActivity;

    public EventDetailsLoader(Activity activity) {
        this.eventActivity = activity;
    }

    public void setTextViewText (TextView textView, Event event, String fieldName) {
        textView.setText(event.getField(fieldName));
    }

    public void set(Event event) {

        if (event != null) {
            // set Activity title to event title
            eventActivity.setTitle(event.getString("eventName"));

            // set banner image
            ParseImageView banner = (ParseImageView) eventActivity.findViewById(R.id.event_details_banner);
            final FrameLayout loader_frame = (FrameLayout) eventActivity.findViewById(R.id.event_details_frame_layout);
            final com.rey.material.widget.ProgressView loader = (com.rey.material.widget.ProgressView) eventActivity.findViewById(R.id.event_details_loading);
            loader.start();
            banner.setParseFile(event.getBanner());
            banner.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    loader_frame.setVisibility(View.GONE);
                    loader.stop();

                }
            });

            // set other textview details
            TextView category = (TextView) eventActivity.findViewById(R.id.ed_category_text);
            setTextViewText(category, event, "eventCategory");

            TextView location = (TextView) eventActivity.findViewById(R.id.ed_location_text);
            setTextViewText(location, event, "eventVenue");

            TextView date = (TextView) eventActivity.findViewById(R.id.ed_date_text);
            setTextViewText(date, event, "eventDate");

        }
    }

}
