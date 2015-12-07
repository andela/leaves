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
        ParseUser user = getCurrentUser();
        user.add("enteredEvents", eventId);
        user.saveInBackground(callback);
    }

    public static boolean isEnteredEvent(String eventId) {
        ParseUser user = getCurrentUser();
        List<String> events = user.getList("enteredEvents");
        List<String> eventIds = (events != null) ? events : new ArrayList<String>();
        return eventIds.contains(eventId);
    }

    public static boolean isLoggedIn() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        return (currentUser != null);
    }
    public static void logoutUser(){
        ParseUser.logOut();
    }
}
