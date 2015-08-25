package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.rey.material.widget.FloatingActionButton;

/**
 * Created by andela on 8/24/15.
 */
public class ItemForm implements View.OnClickListener  {

    Activity activity;
    String eventId;
    String userId;
    FloatingActionButton floatingActionButton;

    EditText name;
    EditText description;
    EditText startBid;
    ImageView cancelAdd;
    ImageView confirmAddBtn;
    ImageButton imageSelectBtn;
    String nameText;

    String descriptionText;
    String startBidText;

    public static ImageView image;
    public static ParseFile file;

    EventItem item;
    ItemListFragment itemListFragment;
    Bundle bundle;

    private static int RESULT_LOAD = 1;

    View view;

    public ItemForm(Activity activity, View view, String eventId, String userId, FloatingActionButton btn) {
        this.view = view;
        this.activity = activity;
        this.eventId = eventId;
        this.userId = userId;
        this.floatingActionButton = btn;
        initialize();
        item = new EventItem();
    }

    private void initialize() {
        name = (EditText) view.findViewById(R.id.new_item_name);
        description = (EditText) view.findViewById(R.id.new_item_description);
        startBid = (EditText) view.findViewById(R.id.new_item_start_bid);

        cancelAdd = (ImageView) view.findViewById(R.id.cancel_add_item_button);
        confirmAddBtn = (ImageView) view.findViewById(R.id.confirm_add_item_button);
        image = (ImageView) view.findViewById(R.id.new_item_image);
        imageSelectBtn = (ImageButton) view.findViewById(R.id.item_image_select_icon);
        imageSelectBtn.setOnClickListener(this);
        confirmAddBtn.setOnClickListener(this);
    }

    public void getData(){
        nameText = name.getText().toString().trim();
        descriptionText = description.getText().toString().trim();
        startBidText = startBid.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_image_select_icon | R.id.new_item_form_image_frame:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        openGallery();
                    }
                }).start();
                break;
            case R.id.confirm_add_item_button:
                add();
                break;
        }
    }

    private void add() {
        getData();
        if(isValid()){
            set();
            save();
        }
    }


    private void save() {

        AsyncTask<Void, Void, Void> itemAsync = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                item.saveAll();
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                itemListFragment = new ItemListFragment();
                bundle = new Bundle();
                bundle.putString("eventId", eventId);
                itemListFragment.setArguments(bundle);
                activity.getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                        .replace(R.id.container, itemListFragment)
                        .addToBackStack(null)
                        .commit();
                floatingActionButton.setVisibility(View.VISIBLE);
            }
        };
        itemAsync.execute();
    }

    private void set() {
        item.setName(nameText);
        item.setDescription(descriptionText);
        item.setPreviousBid(Double.parseDouble(startBidText));
        item.setNewBid(Double.parseDouble(startBidText));
        item.setImage(file);
        item.setEventId(eventId);
        item.setUserId(userId);
    }

    // method to open gallery
    public void openGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        activity.startActivityForResult(galleryIntent, RESULT_LOAD);
    }

    public boolean isValid() {
        boolean valid = true;

        if (nameText.equals("")) {
            name.setError("Item name is required!");
            valid = false;
        }
        if (descriptionText.equals("")) {
            description.setError("Description name is required!");
            valid = false;
        }
        if (startBidText.equals("")) {
            startBid.setError("Start Bid is required!");
            valid = false;
        }
        if(file == null){
            Toast.makeText(activity, "Add an image first", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }
}
