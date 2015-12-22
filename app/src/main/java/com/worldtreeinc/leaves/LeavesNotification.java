package com.worldtreeinc.leaves;

import com.parse.ParsePush;
import com.parse.ParseUser;
import com.worldtreeinc.leaves.model.EventItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

/**
 * Created by andela on 10/7/15.
 */
public class LeavesNotification {

    public static void sendItemBidNotification(Double amount, EventItem item) {
        ParsePush.subscribeInBackground(item.getName() + "-" + item.getObjectId());
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
        String channel = item.getName() + "-" + item.getObjectId();
        pushNotification(channel, data);
    }

    private static void pushNotification(String channel, JSONObject data){
        ParsePush push = new ParsePush();
        push.setData(data);
        push.setChannel(channel);
        push.sendInBackground();
    }

    public static void subscribePlannerToItemChannel(EventItem item) {
        ParsePush.subscribeInBackground(item.getName() + "-" + item.getObjectId());
    }
}