package com.worldtreeinc.leaves.model;


import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.worldtreeinc.leaves.R;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Events")
public class Event extends ParseObject {

    public String getField(String fieldName) {
        return getString(fieldName);
    }

    public Number getEntryFee() {
        return getNumber("entryFee");
    }

    public ParseFile getBanner() {
        return getParseFile("eventBanner");
    }

    public String getUserId() {
        return getString("userId");
    }

    public void setBanner(ParseFile banner) {
        put("eventBanner", banner);
    }

    public void setName(String name) {
        put("eventName", name);
    }

    public String getName() {
        return getString("eventName");
    }

    public void setCategory(String category) {
        put("eventCategory", category);
    }

    public void setDate(String date) {
        put("eventDate", date);
    }

    public void setVenue(String eventVenue) {
        put("eventVenue", eventVenue);
    }

    public void setDescription(String description) {
        put("eventDescription", description);
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setEntryFee(Double entryFee) {
        put("entryFee", entryFee);
    }

    public void incrementEntries() {
        increment("entries");
        saveInBackground();
    }

    public void setEntries() {
        put("entries", 0);
    }

    public int getEntries() {
        return (int) getNumber("entries");
    }

    public static Event getOne(String eventId) {
        Event event;
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        try {
            event = query.get(eventId);
        } catch (ParseException e) {
            e.printStackTrace();
            event = null;
        }
        return event;
    }

    public static List<Event> getAll(String ObjectReference, String column) {
        List<Event> event;
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.whereEqualTo(ObjectReference, column);
        try {
            event = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            event = null;
        }
        return event;
    }
    public static List<Event> getFilter(String ObjectReference, String column) {
        return filter(getAll(ObjectReference, column));
    }

    private static List<Event> filter(List<Event> event) {
        List<Event> newList = new ArrayList<>();
        String id = ParseUser.getCurrentUser().getObjectId();
        for (Event e : event) {
            if (!e.getUserId().equals(id)) {
                newList.add(e);
            }
        }
        return newList;
    }

    public static Event getFirst() {
        Event event = null;
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.orderByDescending("createdAt");
        try {
            event = query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return event;
    }


    public void delete(final Context context) {
        this.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // show a toast
                    Toast.makeText(context, R.string.delete_event_toast_message, Toast.LENGTH_LONG).show();
                    // reload event list after deleting
                    ((Activity) context).recreate();

                }
            }
        });
    }
}
