package com.worldtreeinc.leaves.adapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.fragment.ItemFormFragment;
import com.worldtreeinc.leaves.helper.ItemBidHandler;
import com.worldtreeinc.leaves.model.EventItem;
import com.worldtreeinc.leaves.utility.DialogBox;
import java.lang.reflect.Field;
import java.util.List;
public class EventDetailItemListAdapter extends ArrayAdapter<EventItem> implements PopupMenu.OnMenuItemClickListener {
    private final String eventName;
    private Activity activity;
    private List<EventItem> eventItemList;
    EventItem item;
    FloatingActionButton addItemButton;
    int currentPosition;
    private TextView itemName;
    ItemFormFragment itemFormFragment;
    Bundle bundle;
    private boolean isPlanner;
    private boolean isEnteredEvent;
    public EventDetailItemListAdapter(Activity activity, List<EventItem> eventItemList, boolean isPlanner, boolean isEnteredEvent, String eventName) {
        super(activity, R.layout.event_details_items, eventItemList);
        this.activity = activity;
        this.eventItemList = eventItemList;
        this.isPlanner = isPlanner;
        this.isEnteredEvent = isEnteredEvent;
        this.eventName = eventName;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater listLayoutInflater = LayoutInflater.from(activity);
            convertView = listLayoutInflater.inflate(R.layout.event_details_items, null);
        }
        item = eventItemList.get(position);
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
        if (!isPlanner && !isEnteredEvent) moreActionButton.setVisibility(View.GONE);
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
        itemFormFragment = new ItemFormFragment();
        addItemButton = (FloatingActionButton) activity.findViewById(R.id.add_item_button);
        addItemButton.setVisibility(View.GONE);
        itemFormFragment.setResource(addItemButton);
        bundle = new Bundle();
        bundle.putString("itemId", itemId);
        bundle.putString("eventName", eventName);
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
    @Override
    public boolean onMenuItemClick(MenuItem popMenuItem) {
        final String itemId;
        switch (popMenuItem.getItemId()) {
            case R.id.editEvent:
                item = eventItemList.get(currentPosition);
                itemId = item.getObjectId();
                openForm(itemId);
                return true;
            case R.id.deleteEvent:
                item = eventItemList.get(currentPosition);
                itemId = item.getObjectId();
                new DialogBox().dialog(activity, activity.getString(R.string.delete_item_title), activity.getString(R.string.delete_item_message), new DialogBox.CallBack() {
                    @Override
                    public void onFinished() {
                        delete(itemId);
                    }
                });
                return true;
            case R.id.bidItem:
                new ItemBidHandler(activity, eventItemList, currentPosition).bidItem();
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
        } else {
            popup.inflate(R.menu.bidder_menu_item_list);
        }
        // Force icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            popup.show();
            return;
        }
        popup.show();
    }
}
