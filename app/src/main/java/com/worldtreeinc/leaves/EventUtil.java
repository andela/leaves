package com.worldtreeinc.leaves;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;

/**
 * Created by tunde on 8/11/15.
 */
public class EventUtil {


    static String pathToImage;
    static boolean bannerIsSelected;


    public static ParseFile getByteArray(String filePath) {
        // prepare the image to be sent to parse server
        EventBannerCompressor compressor = new EventBannerCompressor();
        Bitmap bmp = compressor.getCompressed(filePath, 450, 900);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] parseFile = stream.toByteArray();
        ParseFile file = new ParseFile("banner.jpg", parseFile);
        return file;
    }

    public static void processSelectedImage(
            Context context, int requestCode, int resultCode, Intent data, int RESULT_LOAD, int RESULT_OK, ImageView imageView) {
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = context.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                setBannerPath(cursor.getString(columnIndex));
                cursor.close();

                // set the imageView to the selected image
                EventData.setEventBanner(imageView, pathToImage);
                setBannerIsSelected(true);

            }
        } catch (Exception e) {
            Toast.makeText(context, "Please select an image!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public static void setBannerPath(String path) {
        pathToImage = path;
    }

    public static void setBannerIsSelected(boolean status) {
        bannerIsSelected = true;
    }

    public static boolean getBannerSelectedStatus() {
        return bannerIsSelected;
    }

    public static String getBannerPath() {
        return pathToImage;
    }
}