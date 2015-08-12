package com.worldtreeinc.leaves;

import android.content.Intent;

/**
 * Created by andela on 8/11/15.
 */
public interface CreateEventInterface {
    // method to open gallery
    void openGallery();

    // method to be invoked when a picture is selected in the gallery
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
