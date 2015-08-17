package com.worldtreeinc.leaves;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;

import java.util.List;

/**
 * Created by andela on 7/24/15.
 */
public class PlannerEventAdapter extends ArrayAdapter<Event> implements View.OnClickListener{

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<Event> userEventList = null;
    Event event;

    public PlannerEventAdapter(Context context, List<Event> userEventList) {
        super(context, R.layout.planner_event_list_item, userEventList);
        this.context = context;
        this.userEventList = userEventList;
        inflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(context);
    }



    public class ViewHolder {
        TextView eventDescription;
        TextView eventDate;
        TextView eventCategory;
        com.pkmmte.view.CircularImageView eventBanner;
        TextView eventName;
        TextView eventVenue;
        ImageView editButton;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        event = userEventList.get(position);
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.planner_event_list_item, null);

            // Locate the TextViews in listview_item.xml
            holder.eventDescription = (TextView) view.findViewById(R.id.eventDescription);
            holder.eventDate = (TextView) view.findViewById(R.id.eventDate);
            holder.eventCategory = (TextView) view.findViewById(R.id.eventCategory);
            holder.eventName = (TextView) view.findViewById(R.id.eventName);
            holder.eventVenue = (TextView) view.findViewById(R.id.eventVenue);
            holder.editButton = (ImageView) view.findViewById(R.id.editButton);
            // Locate the ImageView in listview_item.xml
            holder.eventBanner = (com.pkmmte.view.CircularImageView) view.findViewById(R.id.eventBanner);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.eventDescription.setText(event.getField("eventDescription"));
        holder.eventDate.setText(event.getField("eventDate"));
        holder.eventCategory.setText(event.getField("eventCategory"));
        holder.eventVenue.setText(event.getField("eventVenue") + " ");
        holder.eventName.setText(event.getField("eventName"));

        ParseFile image = (ParseFile) event.get("eventBanner");
        imageLoader.DisplayImage(image.getUrl(), holder.eventBanner);
        holder.editButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editButton:
                    String eventId = event.getObjectId();
                    Intent intent = new Intent(context, CreateEventActivity.class);
                    intent.putExtra("EVENT_ID", eventId);
                    context.startActivity(intent);
                break;

        }
    }

}