package com.worldtreeinc.leaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.rey.material.widget.Button;

public class EventActivity extends AppCompatActivity  implements View.OnClickListener {

    // global variables to be used in multiple methods.
    private static int RESULT_LOAD = 1;
    EventForm newEventForm;
    Banner banner = new Banner();
    String eventId;
    Button eventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        eventButton = (Button) findViewById(R.id.event_button);

        newEventForm = new EventForm(this);
        setupEdit();

        eventButton.setOnClickListener(this);
        ImageButton openGalleryButton = (ImageButton) findViewById(R.id.banner_select_icon);
        openGalleryButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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
            newEventForm.cancelEvent();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        newEventForm.cancelEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.event_button:
                if (eventId != null) {
                    newEventForm.update(eventId);
                }
                else{
                    newEventForm.create();
                }
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
        banner.processSelectedImage(EventActivity.this, requestCode, resultCode, data, newEventForm, RESULT_LOAD);
    }

    protected void setupEdit() {
        try {
            eventId = getIntent().getExtras().getString("EVENT_ID");

            eventButton.setText("Update Event");
            setTitle("Edit Event");
            // call the set fields method to prefill the form fields
            newEventForm.setData(eventId);
        }
        catch (Exception e) {
            eventId = null;
        }
    }


}
