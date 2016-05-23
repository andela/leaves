package com.worldtreeinc.leaves.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * Created by suadahaji.
 */
public class CameraPermission {
    Activity activity;
    private static int IMAGE_CAPTURE = 3401;

    public CameraPermission(Activity activity) {
        this.activity = activity;
    }

    public void getCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                getImage();
            } else {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 150);
                Toast.makeText(activity, "Your Permission is needed to access the camera", Toast.LENGTH_LONG).show();
            }
        } else {
            getImage();
        }
    }


    public void getImage() {
        Intent getImage = new Intent();
        getImage.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(getImage, IMAGE_CAPTURE);
    }
}
