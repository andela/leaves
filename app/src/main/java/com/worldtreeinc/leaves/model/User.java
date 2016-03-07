package com.worldtreeinc.leaves.model;

import com.parse.ParseClassName;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 8/25/15.
 */
@ParseClassName("User")
public class User extends ParseUser {

    public void setUsername(String username) {
        put("username", username);
    }

    public void setPassword(String password) {
        put("password", password);
    }

    public void setEmail(String email) {
        put("email", email);
    }

    public String getField(String fieldName){
        return getString(fieldName);
    }

    public static void enterEvent(String eventId, SaveCallback callback) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.add("enteredEvents", eventId);
        currentUser.saveInBackground(callback);
    }

    public static boolean isEnteredEvent(String eventId) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        List<String> events = currentUser.getList("enteredEvents");
        List<String> eventIds = (events != null) ? events : new ArrayList<String>();
        return eventIds.contains(eventId);
    }

    public static boolean isLoggedIn() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        return (currentUser != null);
    }

    public static void logout(){
        ParseUser.logOut();
    }

    public static void setItemsBiddedOn(String itemsId, SaveCallback callback) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        List<String> ids = getItemsBiddedOn();
        if(ids == null) {
            currentUser.add("itemsBiddedOnId", itemsId);
        } else {
            if ((ids.contains(itemsId))){
                return;
            } else {
                currentUser.add("itemsBiddedOnId", itemsId);
            }
        }
        currentUser.saveInBackground(callback);
    }

    public static List<String> getItemsBiddedOn() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        return currentUser.getList("itemsBiddedOnId");
    }
}
