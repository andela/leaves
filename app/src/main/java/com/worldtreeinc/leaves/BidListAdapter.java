package com.worldtreeinc.leaves;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BidListAdapter extends ArrayAdapter<BidModel> {

    private Context context;
    private List<BidModel> bids;

    public BidListAdapter(Context context, List<BidModel> objects) {
        super(context, R.layout.activity_planner_dash_bid_item, objects);
        this.context = context;
        this.bids = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater listLayoutInflater = LayoutInflater.from(context);
            convertView = listLayoutInflater.inflate(R.layout.activity_planner_dash_bid_item, null);
        }

        BidModel bid = bids.get(position);

        TextView itemName = (TextView) convertView.findViewById(R.id.planner_dash_item_name);
        itemName.setText(bid.getItemName());

        TextView eventName = (TextView) convertView.findViewById(R.id.planner_dash_event_name);
        eventName.setText(bid.getEventName());

        TextView previousBid = (TextView) convertView.findViewById(R.id.planner_dash_previous_bid);
        previousBid.setText(bid.getPreviousBid().toString());

        TextView newBid = (TextView) convertView.findViewById(R.id.planner_dash_new_bid);
        newBid.setText(bid.getNewBid().toString());

        return convertView;
    }

}
