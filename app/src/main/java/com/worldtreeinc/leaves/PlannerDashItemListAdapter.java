package com.worldtreeinc.leaves;

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

import java.util.List;

public class PlannerDashItemListAdapter extends ArrayAdapter<EventItem> {

    private Context context;
    private List<EventItem> items;

    public PlannerDashItemListAdapter(Context context, List<EventItem> objects) {
        super(context, R.layout.activity_planner_dash_bid_item, objects);
        this.context = context;
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater listLayoutInflater = LayoutInflater.from(context);
            convertView = listLayoutInflater.inflate(R.layout.activity_planner_dash_bid_item, null);
        }


        EventItem item = items.get(position);

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

        return convertView;
    }

}