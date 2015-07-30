package com.worldtreeinc.leaves;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by kamiye on 7/30/15.
 */
public class ParseImageLoader {

    // set class properties
    //private Activity activity;
    private ImageView imageView;
    private String parseTableName;
    private String parseObjectId;

    public ParseImageLoader() {

    }

    public ParseImageLoader(ImageView view, String tableName, String objectId) {
        this.imageView = view;
        this.parseTableName = tableName;
        this.parseObjectId = objectId;
    }


    public void setImage() {

        // Parse code block for pulling event banner
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseTableName);
        query.whereEqualTo("objectId", parseObjectId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                // Change default image only if object returned is not null
                if (object != null)  {
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
        // Parse code block ends

    }

}
