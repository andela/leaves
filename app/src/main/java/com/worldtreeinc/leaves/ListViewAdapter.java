package com.worldtreeinc.leaves;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    public ListViewAdapter(Context context, List<UserEvent> userEventList) {
        this.context = context;
        this.userEventList = userEventList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<UserEvent>();
        this.arraylist.addAll(userEventList);
        imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {
        TextView eventDescription;
        TextView eventDate;
        TextView eventCategory;
        com.pkmmte.view.CircularImageView eventBanner;
        TextView eventName;
        TextView eventVenue;

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
            holder.eventDescription = (TextView) view.findViewById(R.id.eventDescription);
            holder.eventDate = (TextView) view.findViewById(R.id.eventDate);
            holder.eventCategory = (TextView) view.findViewById(R.id.eventCategory);
            holder.eventName = (TextView) view.findViewById(R.id.eventName);
            holder.eventVenue = (TextView) view.findViewById(R.id.eventVenue);

            // Locate the ImageView in listview_item.xml
            holder.eventBanner = (com.pkmmte.view.CircularImageView) view.findViewById(R.id.eventBanner);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.eventDescription.setText(userEventList.get(position).getEventDescription());
        holder.eventDate.setText(userEventList.get(position).getEventDate());
        holder.eventCategory.setText(userEventList.get(position).getEventCategory());
        holder.eventVenue.setText(userEventList.get(position).getEventVenue() + " ");
        holder.eventName.setText(userEventList.get(position).getEventName());


        imageLoader.DisplayImage(userEventList.get(position).getEventBanner(),
                holder.eventBanner);




        return view;
    }

}