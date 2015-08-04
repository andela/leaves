package com.worldtreeinc.leaves;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private ParseObject object;


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
    }

    private ParseQuery<ParseObject> setParseQuery() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseTableName);
        query.whereEqualTo("objectId", parseObjectId);
        return query;
    }


    public void setImage() {
        ParseQuery<ParseObject> query = setParseQuery();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                // Change default image only if object returned is not null
                if (object != null) {
                    ParseFile eventBanner = (ParseFile) object.get("eventBanner");
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
            }
        });
    }


    public void setEventDetails() {
        ParseQuery<ParseObject> query = setParseQuery();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                // Change default image only if object returned is not null
                if (object != null) {
                    if (category != null) {
                        category.setText(object.getString("eventCategory"));
                    }
                    if (location != null) {
                        location.setText(object.getString("eventVenue"));
                    }
                    if (date != null) {
                        date.setText(object.getString("eventDate"));
                    }
                }
            }
        });
    }

}
