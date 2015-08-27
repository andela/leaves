package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.parse.ParseFile;

import java.util.List;

/**
 * Created by andela on 7/24/15.
 */
public class PlannerEventAdapter extends ArrayAdapter<Event> implements PopupMenu.OnMenuItemClickListener {

    // Declare Variables
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<Event> userEventList = null;
    Event event;
    int currentPosition;
    String eventId;
    Dialog dialog = new Dialog();

    public PlannerEventAdapter(Activity activity, List<Event> userEventList) {
        super(activity, R.layout.planner_event_list_item, userEventList);
        this.activity = activity;
        this.userEventList = userEventList;
        inflater = LayoutInflater.from(activity);
        imageLoader = new ImageLoader(activity);
    }

    public class ViewHolder {
        TextView eventDescription;
        TextView eventDate;
        TextView eventCategory;
        com.pkmmte.view.CircularImageView eventBanner;
        TextView eventName;
        TextView eventVenue;
        ImageView moreOptionsButton;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        event = userEventList.get(position);
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.planner_event_list_item, parent, false);

            // Locate the TextViews in listview_item.xml
            holder.eventDescription = (TextView) view.findViewById(R.id.eventDescription);
            holder.eventDate = (TextView) view.findViewById(R.id.eventDate);
            holder.eventCategory = (TextView) view.findViewById(R.id.eventCategory);
            holder.eventName = (TextView) view.findViewById(R.id.eventName);
            holder.eventVenue = (TextView) view.findViewById(R.id.eventVenue);
            holder.moreOptionsButton = (ImageView) view.findViewById(R.id.popMenu);
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

        holder.moreOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = position;
                showMenu(v);
            }
        });

        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editEvent:
                event = userEventList.get(currentPosition);
                String eventId = event.getObjectId();
                Log.v("Event ID", eventId);
                Intent intent = new Intent(activity, EventActivity.class);
                intent.putExtra("EVENT_ID", eventId);
                activity.startActivity(intent);
                activity.finish();
                return true;
            case R.id.deleteEvent:
                event = userEventList.get(currentPosition);
                if (event.getEntries() > 0) {
                    dialog.dialog(activity, activity.getString(R.string.delete_event_title), activity.getString(R.string.delete_event_error));
                    return true;
                } else {
                    dialog.dialog(activity, activity.getString(R.string.delete_event_title), activity.getString(R.string.delete_event_message), new Dialog.CallBack() {
                        @Override
                        public void onFinished() {
                            event.delete(activity);
                        }
                    });
                }
                return true;
            default:
                return false;
        }
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_event_list);
        popup.show();
    }
}