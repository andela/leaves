package com.worldtreeinc.leaves;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Bids")
public class EventItem extends ParseObject {
    public EventItem() {
    }

    public String getEventId() {
        return getString("eventId");
    }

    public void setEventId(String eventId) {
        put("eventId", eventId);
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public Number getPreviousBid() {
        return getNumber("previousBid");
    }

    public void setPreviousBid(Number previousBid) {
        put("previousBid", previousBid);
    }

    public Number getNewBid() {
        return getNumber("newBid");
    }

    public void setNewBid(Number newBid) {
        put("newBid", newBid);
    }

    public String getName() {
        return getString("itemName");
    }

    public void setName(String itemName) {
        put("itemName", itemName);
    }

    public ParseFile getImage() {
        return getParseFile("itemImage");
    }

    public void setImage(ParseFile image) {
        put("itemImage", image);
    }

    public static EventItem getOne(String itemId) {
        EventItem item;
        ParseQuery<EventItem> query = ParseQuery.getQuery(EventItem.class);
        try {
            item = query.get(itemId);

        }
        catch (ParseException e) {
            e.printStackTrace();
            item = null;
        }
        return item;
    }

    public static List<EventItem> getAll(String... args) {
        List<EventItem> item;
        ParseQuery<EventItem> query = ParseQuery.getQuery(EventItem.class);
        if (args.length == 0){
            query.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        }
        else if (args.length == 1){
            query.whereEqualTo("eventId", args[0]);
        }
        try {
            item = query.find();
        }
        catch (ParseException e) {
            e.printStackTrace();
            item = null;
        }
        return item;
    }
}
