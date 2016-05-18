package com.worldtreeinc.leaves.form;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.rey.material.widget.FloatingActionButton;
import com.worldtreeinc.leaves.helper.LeavesNotification;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.fragment.ItemListFragment;
import com.worldtreeinc.leaves.model.Banner;
import com.worldtreeinc.leaves.model.EventItem;



/**
 * Created by andela on 8/24/15.
 */
public class ItemForm implements View.OnClickListener  {

    Activity activity;
    String eventId;
    String userId;
    FloatingActionButton floatingActionButton;
    private boolean mShowingBack = false;
    EditText name;
    EditText description;
    EditText startBid;
    EditText increment;
    com.rey.material.widget.Button confirmAddBtn;
    ImageButton imageSelectBtn;
    String nameText;
    String descriptionText;
    String startBidText;
    String incrementText;
    com.rey.material.widget.Button cancelAddItemButton;
    private String eventName;
    String itemId;
    ProgressDialog progressDialog;
    public static ImageView image;
    public static ParseFile file;
    EventItem item;
    ItemListFragment itemListFragment;
    private static int RESULT_LOAD = 1;
    private static int IMAGE_CAPTURE = 3401;
    View view;

    public ItemForm(Activity activity, View view, String eventId, String userId, FloatingActionButton btn, String itemId, String eventName) {
        this.view = view;
        this.activity = activity;
        this.eventId = eventId;
        this.userId = userId;
        this.floatingActionButton = btn;
        this.eventName = eventName;
        this.itemId = itemId;
        initialize();
        item = new EventItem();
        if(itemId != null) {
            setData();
        }
    }

    private void initialize() {
        name = (EditText) view.findViewById(R.id.new_item_name);
        description = (EditText) view.findViewById(R.id.new_item_description);
        startBid = (EditText) view.findViewById(R.id.new_item_start_bid);
        increment = (EditText) view.findViewById(R.id.increment);

        confirmAddBtn = (com.rey.material.widget.Button) view.findViewById(R.id.confirm_add_item_button);
        confirmAddBtn.setOnClickListener(this);
        image = (ImageView) view.findViewById(R.id.new_item_image);
        image.setOnClickListener(this);
        imageSelectBtn = (ImageButton) view.findViewById(R.id.item_image_select_icon);
        imageSelectBtn.setOnClickListener(this);

        cancelAddItemButton = (com.rey.material.widget.Button) view.findViewById(R.id.cancel_add_item_button);
        cancelAddItemButton.setOnClickListener(this);

    }

    private void setData() {
        EventItem items = EventItem.getOne(itemId);
        eventId = items.getEventId();

        name.setText(items.getName());
        description.setText(items.getDescription());
        startBid.setText(items.getPreviousBid().toString());
        increment.setText(items.getIncrement().toString());
        items.getImage().getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bitmap);
                file = new Banner().getImageFromBitmap(bitmap, "item-banner.jpg");
            }
        });
    }

    public void getData(){
        nameText = name.getText().toString().trim();
        descriptionText = description.getText().toString().trim();
        startBidText = startBid.getText().toString().trim();
        incrementText = increment.getText().toString().trim();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.item_image_select_icon:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (activity.checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
                                captureImage();
                            } else {
                                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 150);
                                Toast.makeText(activity,"Your Permission is needed to get access the camera",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            captureImage();
                        }
                    }
                }).start();
                break;
            case R.id.new_item_image:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                                openGallery();
                            } else {
                                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 180);
                                Toast.makeText(activity,"Your Permission is needed to get access the gallery",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            openGallery();
                        }

                    }
                }).start();
                break;
            case R.id.confirm_add_item_button:
                update();
                break;
            case R.id.cancel_add_item_button:
                flipCard();
                floatingActionButton.setVisibility(v.VISIBLE);
                break;
        }
    }


    private void update() {
        getData();
        if(isValid()){
            if(itemId != null) {
                item = EventItem.getOne(itemId);
            }
            set();
            save();
        }
    }

    public void flipCard() {
        if (mShowingBack) {
            activity.getFragmentManager().popBackStack();
            mShowingBack = false;
            return;
        }

        mShowingBack = true;
        ItemListFragment itemListFragment = new ItemListFragment();
        changeToListFragment(itemListFragment);

    }

    private void changeToListFragment(ItemListFragment itemListFragment) {
        Bundle bundle = new Bundle();
        bundle.putString("eventId", eventId);
        bundle.putBoolean("isPlanner", true);
        itemListFragment.setArguments(bundle);

        activity.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.container, itemListFragment)
                .addToBackStack(null)
                .commit();
    }

    private void save() {
        final AsyncTask<Void, Void, Void> itemAsync = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    item.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage(activity.getString(R.string.event_list_progress_saving));
                progressDialog.setIndeterminate(false);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                itemListFragment = new ItemListFragment();
                changeToListFragment(itemListFragment);
                floatingActionButton.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                LeavesNotification.subscribePlannerToItemChannel(item);
            }
        };
        itemAsync.execute();
    }

    private void set() {
        item.setName(nameText);
        item.setDescription(descriptionText);
        item.setPreviousBid(Double.parseDouble(startBidText));
        item.setNewBid(Double.parseDouble(startBidText));
        item.setIncrement(Double.parseDouble(incrementText));
        item.setImage(file);
        item.setEventId(eventId);
        item.setUserId(userId);
    }

    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galleryIntent, RESULT_LOAD);
    }

    public void captureImage() {
        Intent getImage = new Intent();
        getImage.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(getImage, IMAGE_CAPTURE);
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
        if (incrementText.equals("")) {
            increment.setError("Increment Value is required!");
            valid = false;
        }
        if(file == null){
            Toast.makeText(activity, "Add an image first", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }
}
