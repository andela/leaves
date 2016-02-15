package com.worldtreeinc.leaves.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

/**
 * Created by suadahaji on 2/15/16.
 */
public class GetImageFromCamera {

    public static Uri getUri(Context inContext, Intent data, int requestCode){
        if(requestCode == 1){
            return data.getData();
        }
        Bundle extras = data.getExtras();
        Bitmap imageBitMap = (Bitmap) extras.get("data");
        Uri selectedImage = getImageUri(inContext, imageBitMap);
        return selectedImage;
    }

    private static Uri getImageUri(Context inContext, Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,"Title", null);
        return Uri.parse(path);
    }
}
