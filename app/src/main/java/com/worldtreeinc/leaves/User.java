package com.worldtreeinc.leaves;

import android.content.Intent;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by andela on 8/25/15.
 */
@ParseClassName("User")
public class User extends ParseUser {
    public boolean condition;
    public String errorMessage = null;
    private Exception error = null;

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

    public Exception getError(){
        return error;
    }

    private void setError(Exception e) {
        this.error = e;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

//    private static void setState(boolean bool){
//        condition = bool;
//    }
//
//    public static boolean getState(){
//        return condition;
//    }

    public ParseException register() {
        // let parse handle the
        // remaining validation and register user
        // boolean condition = false;
        ParseException exception = null;
        try {
            signUp();
        }catch(ParseException e){
            e.printStackTrace();
            exception = e;
        }

        return exception;

    }

    public Exception login(String username, String password){

        Exception exception = null;
        try{
            logIn(username, password);
        }catch(Exception e){
            e.printStackTrace();
            exception = e;
        }
        return exception;
    }



}
