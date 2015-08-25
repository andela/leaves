package com.worldtreeinc.leaves;


import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

@ParseClassName("Events")
public class Event extends ParseObject {

    public String getField(String fieldName) {
        return getString(fieldName);
    }

    public ParseFile getBanner() {
        return getParseFile("eventBanner");
    }

    public void setName(String name) {
        put("eventName", name);
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

    public void setBanner(ParseFile banner) {
        put("banner", banner);
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setEntryFee(int entryFee) {
        put("entryFee", entryFee);
    }

    public static Event getOne(String eventId) {
        Event event;
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        try {
            event = query.get(eventId);

        }
        catch (ParseException e) {
            e.printStackTrace();
            event = null;
        }
        return event;
    }

    public static List<Event> getAll(String userId) {
        List<Event> event;
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.whereEqualTo("userId", userId);
        try {
            event = query.find();
        }
        catch (ParseException e) {
            e.printStackTrace();
            event = null;
        }
        return event;
    }

    public Void saveAll(){
        try {
            save();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
