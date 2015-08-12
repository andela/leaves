package com.worldtreeinc.leaves;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.util.ArrayList;

/**
 * Created by kamiye on 7/30/15.
 */
public class ParseEventLoader {

    // set class properties
    private String parseTableName;
    private String parseObjectId;
    private Activity eventActivity;

    public ParseEventLoader(Activity activity, String tableName, String objectId) {

        this.parseTableName = tableName;
        this.parseObjectId = objectId;
        this.eventActivity = activity;
    }

    public void setTextViewText (TextView textView, ParseObject object, String category) {
        textView.setText(object.getString(category));
    }

    public void setBannerImage(final ImageView imageView, ParseObject object, String fieldName) {

        // set event banner
        ParseFile eventBanner = (ParseFile) object.get(fieldName);
        eventBanner.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
            // change default image only if image callback has no exception
            if (e == null) {
                // set the image file
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                //Bitmap songImage = Bitmap.createScaledBitmap(bmp, 100, 100, true);
                imageView.setImageBitmap(bmp);
            }
            }
        });
    }

    public void setEventDetails() {
        ParseObject object = Event.getParseObject(parseTableName, parseObjectId);
        if (object != null) {
            // set Activity title to event title
            eventActivity.setTitle(object.getString("eventName"));

            // set banner image
            ImageView banner = (ImageView) eventActivity.findViewById(R.id.event_details_banner);
            setBannerImage(banner, object, "eventBanner");

            // set other textview details
            TextView category = (TextView) eventActivity.findViewById(R.id.ed_category_text);
            setTextViewText(category, object, "eventCategory");

            TextView location = (TextView) eventActivity.findViewById(R.id.ed_location_text);
            setTextViewText(location, object, "eventVenue");

            TextView date = (TextView) eventActivity.findViewById(R.id.ed_date_text);
            setTextViewText(date, object, "eventDate");

        }
    }

}