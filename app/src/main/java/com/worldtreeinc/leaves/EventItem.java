package com.worldtreeinc.leaves;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Bids")
public class EventItem extends ParseObject {
    public EventItem() {
    }

    public String getEventName() {
        return getString("eventName");
    }

    public void setEventName(String eventName) {
        put("eventName", eventName);
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
}
