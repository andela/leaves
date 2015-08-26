package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.rey.material.widget.FloatingActionButton;

import java.util.List;

public class EventDetailItemListAdapter extends ArrayAdapter<EventItem> {

    private Context context;
    private List<EventItem> items;
    EventItem item;
    FloatingActionButton addItemButton;

    ItemFormFragment itemFormFragment;
    Bundle bundle;

    public EventDetailItemListAdapter(Context context, List<EventItem> objects) {
        super(context, R.layout.event_details_items, objects);
        this.context = context;
        this.items = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater listLayoutInflater = LayoutInflater.from(context);
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
        ParseFile image = (ParseFile) item.getImage();
        image.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                itemImage.setImageBitmap(bitmap);
            }
        });

        ImageView editBtn = (ImageView) convertView.findViewById(R.id.item_details_edit);
        ImageView deleteBtn = (ImageView) convertView.findViewById(R.id.item_details_delete);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = items.get(position);
                String itemId = item.getObjectId();
                openForm(itemId);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = items.get(position);
                String itemId = item.getObjectId();
                delete(itemId);
            }
        });

        return convertView;
    }

    private void openForm(String itemId) {

        itemFormFragment =  new ItemFormFragment();

        addItemButton = (FloatingActionButton) ((Activity) context).findViewById(R.id.add_item_button);
        addItemButton.setVisibility(View.GONE);
        itemFormFragment.setResource(addItemButton);

        bundle = new Bundle();
        bundle.putString("itemId", itemId);
        itemFormFragment.setArguments(bundle);

        ((Activity) context).getFragmentManager()
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
                Toast.makeText(context, "item deleted", Toast.LENGTH_LONG).show();
                ((Activity) context).recreate();
            }
        };
        delete.execute();
    }
}
