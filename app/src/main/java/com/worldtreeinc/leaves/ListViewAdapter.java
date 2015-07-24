package com.worldtreeinc.leaves;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 7/24/15.
 */
public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<UserEvent> userEventList = null;
    private ArrayList<UserEvent> arraylist;

    public ListViewAdapter(Context context, List<UserEvent> worldpopulationlist) {
        this.context = context;
        this.userEventList = worldpopulationlist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<UserEvent>();
        this.arraylist.addAll(worldpopulationlist);
        imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {
        TextView description;
        TextView date;
        TextView category;
        ImageView eventImage;
    }

    @Override
    public int getCount() {
        return userEventList.size();
    }

    @Override
    public Object getItem(int position) {
        return userEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_planner_event_list, null);
            // Locate the TextViews in listview_item.xml
            holder.description = (TextView) view.findViewById(R.id.description);
            holder.date = (TextView) view.findViewById(R.id.date);
            holder.category = (TextView) view.findViewById(R.id.category);
            // Locate the ImageView in listview_item.xml
            holder.eventImage = (ImageView) view.findViewById(R.id.eventImage);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.description.setText(userEventList.get(position).getDescription());
        holder.date.setText(userEventList.get(position).getDate());
        holder.category.setText(userEventList.get(position)
                .getCategory());
        // Set the results into ImageView
        imageLoader.DisplayImage(userEventList.get(position).getEventImage(),
                holder.eventImage);
        // Listen for ListView Item Click

        return view;
    }

}