package com.worldtreeinc.leaves;

import android.app.Activity;
import android.widget.TextView;

import com.parse.ParseImageView;
import com.parse.ParseObject;

/**
 * Created by kamiye on 7/30/15.
 */
public class EventLoader {

    // set class properties
    private String eventId;
    private Activity eventActivity;

    public EventLoader(Activity activity, String eventId) {
        this.eventId = eventId;
        this.eventActivity = activity;
    }

    public void setTextViewText (TextView textView, ParseObject object, String category) {
        textView.setText(object.getString(category));
    }

    public void setEventDetails() {
        Event event = new Event().getEvent(eventId);
        if (event != null) {
            // set Activity title to event title
            eventActivity.setTitle(event.getString("eventName"));

            // set banner image
            ParseImageView banner = (ParseImageView) eventActivity.findViewById(R.id.event_details_banner);
            banner.setParseFile(event.getEventBanner());
            banner.loadInBackground();

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
