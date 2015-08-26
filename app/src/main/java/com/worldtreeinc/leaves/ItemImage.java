package com.worldtreeinc.leaves;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by andela on 8/25/15.
 */
public class ItemImage {

    public void set(Activity activity, Uri image){
        ItemForm.image.setImageURI(image);
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), image);
            // Convert it to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] parseFile = stream.toByteArray();
            ItemForm.file = new ParseFile("image.jpg", parseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
