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
public class ParseObjectLoader {

    // set class properties
    private TextView location;
    private TextView category;
    private TextView date;
    private ImageView imageView;
    private String parseTableName;
    private String parseObjectId;
    private Activity eventActivity;



    public ParseObjectLoader() {

    }

    public ParseObjectLoader(Activity activity, String tableName, String objectId) {

        // get ImageView of event banner to change
        ImageView banner = (ImageView) activity.findViewById(R.id.event_details_banner);

        // get textviews for details of the event
        TextView category = (TextView) activity.findViewById(R.id.ed_category_text);
        TextView location = (TextView) activity.findViewById(R.id.ed_location_text);
        TextView date = (TextView) activity.findViewById(R.id.ed_date_text);

        this.location = location;
        this.category = category;
        this.date = date;
        this.imageView = banner;
        this.parseTableName = tableName;
        this.parseObjectId = objectId;
        this.eventActivity = activity;
    }

    private ParseObject getParseObject() {
        ParseObject object;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseTableName);
        try {
           object = query.get(parseObjectId);
        }
        catch (ParseException e) {
            e.printStackTrace();
            object = null;
        }

        return object;
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
        ParseObject object = getParseObject();
        if (object != null) {
            // set Activity title to event title
            eventActivity.setTitle(object.getString("eventName"));

            // set banner image
            setBannerImage(imageView, object, "eventBanner");

            // set other textview details
            if (category != null) {
                setTextViewText(category, object, "eventCategory");
            }
            if (location != null) {
                setTextViewText(location, object, "eventVenue");
            }
            if (date != null) {
                setTextViewText(date, object, "eventDate");
            }
        }
    }

}
