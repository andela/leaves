package com.worldtreeinc.leaves;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;

/**
 * Created by tunde on 8/11/15.
 */
public class EventUtil {

    String pathToImage;
    boolean bannerIsSelected;

    public EventUtil() {}

    public void dialog(Activity activity) {
        final Activity eventActivity = activity;
        // build up the dialog
        new AlertDialog.Builder(eventActivity)
                .setTitle("Cancel Event")
                .setMessage("Are you sure you want to cancel event? You will lose all data entered.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        // close the form and return to the dashboard
                        backToDash(eventActivity);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void backToDash(Activity activity) {
        Intent intent = new Intent(activity, PlannerDashActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }


    public ParseFile getByteArray(String filePath) {
        // prepare the image to be sent to parse server
        EventBannerCompressor compressor = new EventBannerCompressor();
        Bitmap bmp = compressor.getCompressed(filePath, 450, 900);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] parseFile = stream.toByteArray();
        ParseFile file = new ParseFile("banner.jpg", parseFile);
        return file;
    }

    public String getBannerPath() {
        return pathToImage;
    }

    public boolean getBannerSelectedStatus() {
        return bannerIsSelected;
    }
}