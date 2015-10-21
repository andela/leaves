package com.worldtreeinc.leaves.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.worldtreeinc.leaves.activity.PaymentOptionActivity;
import com.worldtreeinc.leaves.helper.ImageLoader;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.activity.EventActivity;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.model.EventItem;
import com.worldtreeinc.leaves.model.User;
import com.worldtreeinc.leaves.utility.DialogBox;
import com.worldtreeinc.leaves.utility.ParseProxyObject;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by andela on 7/24/15.
 */
public class EventsListAdapter extends ArrayAdapter<Event> implements PopupMenu.OnMenuItemClickListener {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<Event> userEventList = null;
    Event event;
    int currentPosition;
    String eventId;
    DialogBox dialog = new DialogBox();
    boolean isPlanner;

    public EventsListAdapter(Context context, List<Event> userEventList, boolean isPlanner) {
        super(context, R.layout.event_list_item, userEventList);
        this.isPlanner = isPlanner;
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
        ImageView moreOptionsButton;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        event = userEventList.get(position);

        // get event Id
        String eventId = event.getObjectId();
        boolean isEnteredEvent = User.isEnteredEvent(eventId);

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.event_list_item, parent, false);

            // Locate the TextViews in listview_item.xml
            holder.eventDescription = (TextView) view.findViewById(R.id.eventDescription);
            holder.eventDate = (TextView) view.findViewById(R.id.eventDate);
            holder.eventCategory = (TextView) view.findViewById(R.id.eventCategory);
            holder.eventName = (TextView) view.findViewById(R.id.eventName);
            holder.eventVenue = (TextView) view.findViewById(R.id.eventVenue);
            holder.moreOptionsButton = (ImageView) view.findViewById(R.id.popMenu);

            // condition to check if user is not planner and has entered event
            if (!isPlanner && isEnteredEvent) holder.moreOptionsButton.setVisibility(View.GONE);

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

    public Event getCurrentEvent(int position)  {
        return this.userEventList.get(position);
    }

    @Override
    public boolean onMenuItemClick(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editEvent:
                editEvent();
                return true;
            case R.id.deleteEvent:
                deleteEvent();
                return true;
            case R.id.enterEvent:
                enterEvent();
                return true;
            default:
                return false;
        }
    }

    // method to be called when the enterEvent button is clicked
    private void enterEvent() {
        event = userEventList.get(currentPosition);
        Intent intent = new Intent(context, PaymentOptionActivity.class);

        ParseProxyObject proxyObject = new ParseProxyObject(event);
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", proxyObject);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void editEvent() {
        event = userEventList.get(currentPosition);
        eventId = event.getObjectId();
        Log.v("Event ID", eventId);
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra("EVENT_ID", eventId);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    private void deleteEvent() {
        event = userEventList.get(currentPosition);
        eventId = event.getObjectId();
        if (event.getEntries() > 0) {
            dialog.dialog(context, context.getString(R.string.delete_event_title), context.getString(R.string.delete_event_error));
        } else {
            dialog.dialog(context, context.getString(R.string.delete_event_title), context.getString(R.string.delete_event_message), new DialogBox.CallBack() {
                @Override
                public void onFinished() {
                    // delete all items related to event
                    List<EventItem> items = EventItem.getByEventId(eventId);
                    int i = 0;
                    while (i < items.size()) {
                        items.get(i).deleteInBackground();
                        i++;
                    }
                    // delete event
                    event.delete(context);
                }
            });
        }
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.setOnMenuItemClickListener(this);
        if (isPlanner) {
            popup.inflate(R.menu.menu_event_list);
        } else {
            popup.inflate(R.menu.bidder_menu_event_list);
        }
        // Force icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            Log.w("TAG", "error forcing menu icons to show", e);
            popup.show();
            return;
        }
        popup.show();
    }
}