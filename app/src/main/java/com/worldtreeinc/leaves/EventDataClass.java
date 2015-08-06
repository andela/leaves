package com.worldtreeinc.leaves;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

/**
 * Created by andela on 8/5/15.
 */
public class EventDataClass {
    EditText eventDateEditText;
    Context context;
    ParseFile file;
    ;
    public EventDataClass(Context context,EditText eventDateEditText){
        this.context = context;
        this.eventDateEditText = eventDateEditText;

    }

    public EventDataClass(){}

    public void selectDate() {
        int mYear, mMonth, mDay;
        // Process to get Current Date
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Display Selected date in text box
                eventDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }


    public void setEventBanner(ImageView eventBannerImageView, String imagePath) {
        // create a new banner compressor object
        EventBannerCompressor compressor = new EventBannerCompressor();

        // Set the Image in ImageView after decoding the String
        Bitmap bitmap = compressor.getCompressed(imagePath, 450, 900);
        eventBannerImageView.setImageBitmap(bitmap);

        // clear bitmap
        bitmap = null;
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
        file = new ParseFile("banner.jpg", parseFile);
        // clear bitmap
        bmp = null;
        return file;
    }

}
