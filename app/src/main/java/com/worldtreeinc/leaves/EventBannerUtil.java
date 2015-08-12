package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;

/**
 * Created by andela on 8/12/15.
 */
public class EventBannerUtil {

    public String imagePath;

    public EventBannerUtil() {
    }

    public void processSelectedImage(Activity activity, int requestCode, int resultCode, Intent data, EventForm newEventForm, int RESULT_LOAD) {
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD && resultCode == activity.RESULT_OK && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                cursor.close();

                // set the imageView to the selected image
                setEventBanner(newEventForm.eventBannerImageView, imagePath);
                setBannerSelected(true, newEventForm);
                setBannerPath(imagePath, newEventForm);

            }
        } catch (Exception e) {
            Toast.makeText(activity, "Please select an image!", Toast.LENGTH_LONG)
                    .show();
        }
    }


    public void setEventBanner(ImageView eventBannerImageView, String imagePath) {
        // create a new banner compressor object
        EventBannerCompressor compressor = new EventBannerCompressor();

        // Set the Image in ImageView after decoding the String
        Bitmap bitmap = compressor.getCompressed(imagePath, 450, 900);
        eventBannerImageView.setImageBitmap(bitmap);
    }

    public void clearEventBanner(Activity activity, ImageView bannerImageView) {
        Drawable drawable;
        if (android.os.Build.VERSION.SDK_INT < 21) {
            drawable = activity.getResources().getDrawable(R.drawable.default_image);
        } else {
            drawable = activity.getApplicationContext().getDrawable(R.drawable.default_image);
        }
        bannerImageView.setImageDrawable(drawable);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        Log.v("Bitmap width - Height :", width + " : " + height);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
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

    public void setBannerSelected(boolean status, EventForm eventForm) {
        eventForm.bannerSelected = status;
    }

    public void setBannerPath(String path, EventForm eventForm) {
        eventForm.imagePath = path;
    }
}