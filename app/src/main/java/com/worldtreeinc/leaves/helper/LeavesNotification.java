package com.worldtreeinc.leaves.helper;

import com.parse.ParsePush;
import com.parse.ParseUser;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.model.EventItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

/**
 * Created by andela on 10/7/15.
 */
public class LeavesNotification {

    public static void sendItemBidNotification(Double amount, EventItem item) {
        String channel = "Leaves" + "-" + item.getObjectId();
        ParsePush.subscribeInBackground(channel);
        String message = ParseUser.getCurrentUser().getUsername() +
                " placed a bid of " + NumberFormat.getCurrencyInstance().format(amount) + " on " + item.getName();
        String eventId = item.getEventId();
        JSONObject data = new JSONObject();
        try {
            data.put("alert", message);
            data.put("eventId", eventId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pushNotification(channel, data);
    }

    public static void sendItemAddNotification(EventItem item, String eventName) {
        String channel = "Leaves" + "-" + item.getEventId();
        ParsePush.subscribeInBackground(channel);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(item.getName());
        stringBuilder.append(" has been added to ");
        stringBuilder.append(eventName);
        stringBuilder.append(" event.");
        JSONObject data = new JSONObject();
        try {
            data.put("alert", stringBuilder.toString());
            data.put("eventId", item.getEventId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        pushNotification(channel, data);
    }

    private static void pushNotification(String channel, JSONObject data){
        ParsePush push = new ParsePush();
        push.setData(data);
        push.setChannel(channel);
        push.sendInBackground();
    }

    public static void subscribePlannerToItemChannel(EventItem item) {
        ParsePush.subscribeInBackground("Leaves" + "-" + item.getObjectId());
    }

    public static void subscribePlannerToEventChannel(Event event) {
        ParsePush.subscribeInBackground("Leaves" + "-" + event.getObjectId());
    }

    public static void subscribeBidderToEventChannel(String eventId) {
        ParsePush.subscribeInBackground("Leaves" + "-" + eventId);
    }
}
