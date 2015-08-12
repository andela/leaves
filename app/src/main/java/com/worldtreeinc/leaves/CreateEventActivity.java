package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

public class CreateEventActivity extends AppCompatActivity  implements View.OnClickListener {

    // global variables to be used in multiple methods.
    private static int RESULT_LOAD = 1;
    String imagePath;
    ProgressView progressView;
    Event event = new Event(); // event object
    Activity createEventActivity = this;
    CreateEvent newEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);

        newEvent = new CreateEvent(this);

        Button createEventButton = (Button) findViewById(R.id.create_event_button);
        createEventButton.setOnClickListener(this);
        ImageButton openGalleryButton = (ImageButton) findViewById(R.id.banner_select_icon);
        openGalleryButton.setOnClickListener(this);

    }
//    CreateEvent newEvent = new CreateEvent(createEventActivity);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.create_event_cancel) {
            newEvent.cancelEvent();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        newEvent.cancelEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_event_button:
                newEvent.create();
                break;
            case R.id.banner_select_icon:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        openGallery();
                    }
                }).start();
                break;
        }
    }

    // method to open gallery
    public void openGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                cursor.close();

                // set the imageView to the selected image
                new EventData().setEventBanner(newEvent.eventBannerImageView, imagePath);
                newEvent.setBannerSelected(true);
                newEvent.setBannerPath(imagePath);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please select an image!", Toast.LENGTH_LONG)
                    .show();
        }
    }
}