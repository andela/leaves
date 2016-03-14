package com.worldtreeinc.leaves.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.model.EventItem;

import java.util.List;

/**
 * Created by andela on 09/03/2016.
 */
public class MyBidsAdapter extends ArrayAdapter<EventItem> {
    private List<EventItem> eventItemList;
    Context context;


    public MyBidsAdapter(Context context, List<EventItem> eventItems) {
        super(context, 0, eventItems);
        this.context = context;
        this.eventItemList = eventItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventItem item;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_details_items, parent, false);
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
                if (bytes.length != 0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    itemImage.setImageBitmap(bitmap);
                }
            }
        });
        return convertView;

    }
}
