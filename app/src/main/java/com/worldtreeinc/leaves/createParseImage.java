/*
package com.worldtreeinc.leaves;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.parse.ParseFile;
import com.rey.material.widget.ProgressView;

import java.io.ByteArrayOutputStream;

*/
/**
 * Created by andela on 8/11/15.
 *//*


public class createParseImage {

    boolean bannerSelected;
    Activity activity;
    String imagePath;
    ParseFile file;

    public createParseImage(boolean bannerSelected, Activity activity, String imagePath) {
        this.bannerSelected = bannerSelected;
        this.activity = activity;
        this.imagePath = imagePath;
//        new ParseImageSelector().execute();//
    }

//    public ParseFile getFile() {
//        return file;
//    }

    // private async class to handle heavy image loading to parse
    public class ParseImageSelector extends AsyncTask<ParseFile, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // start the progress view
            ProgressView progressView = (ProgressView) activity.findViewById(R.id.progress_view);
            progressView.start();
        }
        @Override
        protected ParseFile doInBackground(ParseFile... params) {
            if (bannerSelected) {
                file = EventUtil.getByteArray(imagePath);
            } else {
                // set default banner for event
                Drawable drawable;
                if (android.os.Build.VERSION.SDK_INT < 21) {
                    drawable = activity.getResources().getDrawable(R.drawable.default_image);
                } else {
                    drawable = activity.getApplicationContext().getDrawable(R.drawable.default_image);
                }
                Bitmap bitmap = new EventData().drawableToBitmap(drawable);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] parseFile = stream.toByteArray();
                file = new ParseFile("banner.jpg", parseFile);
            }
            return file;
        }
        @Override
        protected void onPostExecute(Void result) {
            // after all validation has passed, send the image to the server
            //sendEventBannerToParse();
        }
    }

}
*/
