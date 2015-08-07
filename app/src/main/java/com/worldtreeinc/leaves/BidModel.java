package com.worldtreeinc.leaves;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;

@ParseClassName("Bids")
public class BidModel extends ParseObject {
    public BidModel() {
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

    public String getItemName() {
        return getString("itemName");
    }

    public void setItemName(String itemName) {
        put("itemName", itemName);
    }

    public ParseFile getItemImage() {
        return getParseFile("itemImage");
    }

    public void setItemImage(ParseFile itemImage) {
        put("itemImage", itemImage);
    }
}
