package com.worldtreeinc.leaves;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 7/14/15.
 */
public class UserAuth {
    private String Username;
    private String UserPassword;
    private Activity activity;

    public UserAuth(Activity activity, String username, String userPassword) {
        this.Username = username;
        this.UserPassword = userPassword;
        this.activity = activity;
    }

    public void login(){
        ParseUser.logInInBackground(this.Username, this.UserPassword, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    setToastMessage("You are logged in");
                    Intent roleIntent = new Intent(activity, RoleOptionActivity.class);
                    activity.startActivity(roleIntent);
                } else {
                    // Login failed. Look at the ParseException to see what happened.
                    setToastMessage(e.getMessage());
                    return;
                }
            }
        });
    }

    public void FacebookLogin(){
        List<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        ParseFacebookUtils.logInWithReadPermissionsInBackground(activity, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                Class activitySwitch;
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                    setToastMessage("Login failed");
                    return;
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    setToastMessage("Your account has been created!");
                    activitySwitch = RoleOptionActivity.class;
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    setToastMessage("You are logged in");
                    activitySwitch = RoleOptionActivity.class;
                }
                Intent intent = new Intent(activity, activitySwitch);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });
    }

    //set toast message function as it is used repeatedly
    private void setToastMessage (String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

}
