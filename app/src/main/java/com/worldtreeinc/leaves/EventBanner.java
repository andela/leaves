package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by andela on 8/12/15.
 */
public class EventBanner {

    public String imagePath;

    public EventBanner() {
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
                set(newEventForm.eventBannerImageView, imagePath);
                setSelected(true, newEventForm);
                setPath(imagePath, newEventForm);

            }
        } catch (Exception e) {
            Toast.makeText(activity, "Please select an image!", Toast.LENGTH_LONG)
                    .show();
        }
    }


    public void set(ImageView eventBannerImageView, String imagePath) {
        eventBannerImageView.setImageBitmap(new EventBannerCompressor().getCompressed(imagePath, 450, 900));
    }

    public void clear(Activity activity, ImageView bannerImageView) {
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

    public void setSelected(boolean status, EventForm eventForm) {
        eventForm.bannerSelected = status;
    }

    public void setPath(String path, EventForm eventForm) {
        eventForm.imagePath = path;
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

}
