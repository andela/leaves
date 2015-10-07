package com.worldtreeinc.leaves;

import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;

import java.text.NumberFormat;

/**
 * Created by andela on 10/7/15.
 */
public class LeavesNotification {

    public static void sendItemBidNotification(Double amount, EventItem item){
        ParsePush.subscribeInBackground(item.getName() + "-" + item.getObjectId());
        String message = ParseUser.getCurrentUser().getUsername() +
                " placed a bid of " + NumberFormat.getCurrencyInstance().format(amount) + " on " + item.getName();
        ParsePush push = new ParsePush();
        push.setChannel(item.getName() + "-" + item.getObjectId());
        push.setMessage(message);
        push.sendInBackground();
    }
}
