package com.worldtreeinc.leaves.model;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.parse.ParseFile;
import com.worldtreeinc.leaves.form.ItemForm;
import com.worldtreeinc.leaves.utility.EventBannerCompressor;

import java.io.ByteArrayOutputStream;

/**
 * Created by andela on 8/25/15.
 */
public class ItemImage {

    public void set(Activity activity, Uri image){
        Bitmap bitmap;
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            String imagePath;
            // Get the cursor
            Cursor cursor = activity.getContentResolver().query(image, filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();

            // set the imageView to the selected image
            ItemForm.image.setImageBitmap(new EventBannerCompressor().getCompressed(imagePath, 300, 300));

             getByteArray(imagePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getByteArray(String filePath) {
        // prepare the image to be sent to parse server
        EventBannerCompressor compressor = new EventBannerCompressor();
        Bitmap bmp = compressor.getCompressed(filePath, 450, 900);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] parseFile = stream.toByteArray();
        ItemForm.file = new ParseFile("item.jpg", parseFile);
    }

}