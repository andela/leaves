package com.worldtreeinc.leaves.model;

import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Items")
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

    public Number getIncrement() {
        return getNumber("increment");
    }

    public void setIncrement(Number increment) {
        put("increment", increment);
    }

    public Number getNewBid() {
        return getNumber("newBid");
    }

    public void setNewBid(Number newBid) {
        put("newBid", newBid);
    }

    public void setName(String itemName) {
        put("itemName", itemName);
    }

    public String getName() {
        return getString("itemName");
    }

    public String getDescription() {
        return getString("itemDescription");
    }

    public void setDescription(String itemDescription) {
        put("itemDescription", itemDescription);
    }

    public ParseFile getImage() {
        return getParseFile("itemImage");
    }

    public void setImage(ParseFile image) {
        put("itemImage", image);
    }

    public void setItemDetail(ParseObject itemDetails) {
        put("itemDetails", itemDetails);
    }

    public ParseObject getItemDetail() {
        return getParseObject("itemDetails");
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

    public static EventItem getFirstByUserId() {
        EventItem item = null;
        ParseQuery<EventItem> query = ParseQuery.getQuery(EventItem.class);
        query.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        query.orderByDescending("createdAt");
        try {
            item = query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<EventItem> getByEventId(String eventId) {
        List<EventItem> item;
        ParseQuery<EventItem> query = ParseQuery.getQuery(EventItem.class);
        query.whereEqualTo("eventId", eventId);
        try {
            item = query.find();
        }
        catch (ParseException e) {
            e.printStackTrace();
            item = null;
        }
        return item;
    }

    public void refreshItem(final EventItem.ItemRefreshCallBack callback){
        fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                callback.onRefresh((EventItem) parseObject);
            }
        });
    }

    public interface ItemRefreshCallBack{
        void onRefresh(EventItem item);
    }
}
