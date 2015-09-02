package com.worldtreeinc.leaves;

import android.app.Activity;
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

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by tunde on 9/2/15.
 */


public class BidderEventListAdapter extends ArrayAdapter<Event> implements PopupMenu.OnMenuItemClickListener {

    // Declare Variables
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<Event> userEventList = null;
    Event event;
    int currentPosition;
    String eventId;


    public BidderEventListAdapter(Activity activity, List<Event> plannerEventList) {
        super(activity, R.layout.planner_event_list_item, plannerEventList);
        this.activity = activity;
        this.userEventList = plannerEventList;
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
    public boolean onMenuItemClick(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.enterEvent:
                enterEvent();
                return true;
            default:
                return false;
        }
    }

    // method to be called when the enterEvent button is clicked
    private void enterEvent() {
        Toast.makeText(activity, "Event Entered!", Toast.LENGTH_LONG).show();
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.bidder_menu_event_list);
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
