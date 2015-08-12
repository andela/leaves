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

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setEventEntryFee(int entryFee) {
        put("entryFee", entryFee);
    }

    public static ParseObject getParseObject(String parseTableName, String parseObjectId) {
        ParseObject object;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseTableName);
        try {
            object = query.get(parseObjectId);
        }
        catch (ParseException e) {
            e.printStackTrace();
            object = null;
        }

        return object;
    }

}
