package com.worldtreeinc.leaves;


import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Events")
public class Event extends ParseObject {

    public Event() {

    }

    public void setEventName(String eventName) {
        put("eventName", eventName);
    }

    public void setEventCategory(String eventCategory) {
        put("eventCategory", eventCategory);
    }

    public void setEventDate(String eventDate) {
        put("eventDate", eventDate);
    }

    public void setEventVenue(String eventVenue) {
        put("eventVenue", eventVenue);
    }

    public void setEventDescription(String eventDescription) {
        put("eventDescription", eventDescription);
    }

    public void setEventBanner(ParseFile eventBanner) {
        put("eventBanner", eventBanner);
    }

    public ParseFile getEventBanner() {
        return getParseFile("eventBanner");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setEventEntryFee(int entryFee) {
        put("entryFee", entryFee);
    }

    public Event getEvent(String eventId) {
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

}
