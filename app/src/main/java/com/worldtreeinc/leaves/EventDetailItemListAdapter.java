package com.worldtreeinc.leaves;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.rey.material.widget.FloatingActionButton;

import java.lang.reflect.Field;
import java.util.List;

public class EventDetailItemListAdapter extends ArrayAdapter<EventItem> implements PopupMenu.OnMenuItemClickListener {

    private Activity activity;
    private List<EventItem> items;
    EventItem item;
    FloatingActionButton addItemButton;
    int currentPosition;

    ItemFormFragment itemFormFragment;
    Bundle bundle;
    private boolean isPlanner;

    public EventDetailItemListAdapter(Activity activity, List<EventItem> objects, boolean isPlanner) {
        super(activity, R.layout.event_details_items, objects);
        this.activity = activity;
        this.items = objects;
        this.isPlanner = isPlanner;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater listLayoutInflater = LayoutInflater.from(activity);
            convertView = listLayoutInflater.inflate(R.layout.event_details_items, null);
        }

        item = items.get(position);

        TextView itemName = (TextView) convertView.findViewById(R.id.event_details_item_name);
        itemName.setText(item.getName());

        TextView itemDescription = (TextView) convertView.findViewById(R.id.event_details_item_description);
        itemDescription.setText(item.getDescription());

        TextView previousBid = (TextView) convertView.findViewById(R.id.event_details_previous_bid);
        previousBid.setText(item.getPreviousBid().toString());

        TextView newBid = (TextView) convertView.findViewById(R.id.event_details_new_bid);
        newBid.setText(item.getNewBid().toString());

        final ImageView itemImage = (ImageView) convertView.findViewById(R.id.event_details_item_image);
        item.getImage().getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                itemImage.setImageBitmap(bitmap);
            }
        });

        ImageView moreActionButton = (ImageView) convertView.findViewById(R.id.popMenu);

        moreActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = position;
                showMenu(v);
            }
        });
        return convertView;
    }

    private void openForm(String itemId) {

        itemFormFragment =  new ItemFormFragment();

        addItemButton = (FloatingActionButton) activity.findViewById(R.id.add_item_button);
        addItemButton.setVisibility(View.GONE);
        itemFormFragment.setResource(addItemButton);

        bundle = new Bundle();
        bundle.putString("itemId", itemId);
        itemFormFragment.setArguments(bundle);

        activity.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.container, itemFormFragment)
                .addToBackStack(null)
                .commit();
    }

    private void delete(final String itemId) {
        AsyncTask<Void, Void, Void> delete = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                item = EventItem.getOne(itemId);
                try {
                    item.delete();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(activity, "item deleted", Toast.LENGTH_LONG).show();
                activity.recreate();
            }
        };
        delete.execute();
    }

    private void bidItem() {
        Toast.makeText(activity, "Bid Made!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem popMenuItem) {
        final String itemId;
        switch (popMenuItem.getItemId()) {
            case R.id.editEvent:
                item = items.get(currentPosition);
                itemId = item.getObjectId();
                openForm(itemId);
                return true;
            case R.id.deleteEvent:
                item = items.get(currentPosition);
                itemId = item.getObjectId();
                new Dialog().dialog(activity, activity.getString(R.string.delete_item_title), activity.getString(R.string.delete_item_message), new Dialog.CallBack() {
                    @Override
                    public void onFinished() {
                        delete(itemId);
                    }
                });

                return true;
            case R.id.bidItem:
                bidItem();
                return true;
            default:
                return false;
        }
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(this);
        if (isPlanner) {
            popup.inflate(R.menu.menu_event_list);
        }
        else {
            popup.inflate(R.menu.bidder_menu_item_list);
        }
        // Force icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            Log.w("TAG", "error forcing menu icons to show", e);
            popup.show();
            return;
        }
        popup.show();
    }
}
