package com.worldtreeinc.leaves;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseImageView;

import java.util.List;

public class ItemListAdapter extends ArrayAdapter<EventItem> {

    private Context context;
    private List<EventItem> items;

    public ItemListAdapter(Context context, List<EventItem> objects) {
        super(context, R.layout.activity_planner_dash_bid_item, objects);
        this.context = context;
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater listLayoutInflater = LayoutInflater.from(context);
            convertView = listLayoutInflater.inflate(R.layout.activity_planner_dash_bid_item, null);
        }

        EventItem item = items.get(position);
        Event event =  Event.getOne(item.getEventId());

        TextView itemName = (TextView) convertView.findViewById(R.id.planner_dash_item_name);
        itemName.setText(item.getName());

        TextView eventName = (TextView) convertView.findViewById(R.id.planner_dash_event_name);
        eventName.setText(event.getField("eventName"));

        TextView previousBid = (TextView) convertView.findViewById(R.id.planner_dash_previous_bid);
        previousBid.setText(item.getPreviousBid().toString());

        TextView newBid = (TextView) convertView.findViewById(R.id.planner_dash_new_bid);
        newBid.setText(item.getNewBid().toString());

        final ParseImageView itemImage = (ParseImageView) convertView.findViewById(R.id.item_image);
        itemImage.setParseFile(item.getImage());
        itemImage.loadInBackground();

        return convertView;
    }

}
