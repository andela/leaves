package com.worldtreeinc.leaves.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rey.material.widget.Button;
import com.worldtreeinc.leaves.model.Banner;
import com.worldtreeinc.leaves.form.EventForm;
import com.worldtreeinc.leaves.R;

public class EventActivity extends AppCompatActivity  implements View.OnClickListener {

    private static int RESULT_LOAD = 1;
    EventForm newEventForm;
    Banner banner = new Banner();
    String eventId;
    Button eventButton;
    private static int REQUEST_CAMERA = 3401;

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

        ImageView openGallery = (ImageView) findViewById(R.id.event_banner);
        openGallery.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
            case R.id.event_banner:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        openGallery();
                    }
                }).start();
                break;
            case R.id.banner_select_icon:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        captureImage();
                    }
                }).start();
                break;
        }
    }

    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD);

    }
    public void captureImage() {
        Intent getImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getImage.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(getImage, REQUEST_CAMERA);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        banner.processSelectedImage(EventActivity.this, requestCode, resultCode, data, newEventForm,requestCode);
    }

    protected void setupEdit() {
        try {
            eventId = getIntent().getExtras().getString("EVENT_ID");

            eventButton.setText("Update Event");
            setTitle("Edit Event");
            newEventForm.setData(eventId);
        }
        catch (Exception e) {
            eventId = null;
        }
    }


}
