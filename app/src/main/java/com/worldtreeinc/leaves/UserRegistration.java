package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rey.material.widget.ProgressView;

/**
 * Created by kamiye on 7/15/15.
 */
public class UserRegistration {

    static ProgressView loader;
    private static Activity activity;
    private static Context context;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public UserRegistration(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // different method to add context and activity
    // for testing capability
    public boolean setContext (Context context) {
        if (context != null) {
            UserRegistration.context = context;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean setActivity (Activity activity) {
        if (activity != null) {
            UserRegistration.activity = activity;
            return true;
        }
        else {
            return false;
        }
    }

    // set loader method
    // activity must be set to be able to use this method
    public boolean setLoader() {
        if (UserRegistration.activity != null) {
            loader = (ProgressView) UserRegistration.activity.findViewById(R.id.loading);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * set getter methods to check for the class functionality and constructors
     */

    // for username
    public String getUsername() {
        return this.username;
    }

    // for email
    public String getEmail() {
        return this.email;
    }



    public boolean isValid() {
        // for password, only check for equality in order to protext password
        String message;

        if (this.email.equals("")) {
            message = "Email field must not be empty or blank";
            setToastMessage(message);
            return false;
        }

        if (this.password.equals("")) {
            message = "Password field must not be empty or blank";
            setToastMessage(message);
            return false;
        }

        if (!this.password.equals(this.confirmPassword)) {
            message = "Password field does not match confirm password";
            setToastMessage(message);
            return false;
        }

        return true;
    }

    //set toast message function as it is used repeatedly
    public void setToastMessage (String message) {
        Toast.makeText(UserRegistration.context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Run local validity checks before moving over to parse
     */


    public void register() {

        // define new parse user object
        ParseUser user = new ParseUser();

        // set the user properties
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(this.email);

        // let parse handle the remaining validation and register user
        UserRegistration.loader.start();
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    String message = "Sign Up Successful";
                    UserRegistration.loader.stop();

                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                // start new activity when login is successful
                                Intent intent = new Intent(UserRegistration.context, RoleOptionActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                UserRegistration.context.startActivity(intent);
                            } else {
                                // change activity to login if sign up was successful but login failed
                                Intent intent = new Intent(UserRegistration.context, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                UserRegistration.context.startActivity(intent);
                            }
                        }
                    });

                } else {
                    String message;
                    // get error message from parse
                    if (e.getCause() != null) {
                        message = e.getCause().getMessage();
                    } else {
                        message = e.getMessage();
                    }
                    UserRegistration.loader.stop();
                    setToastMessage(message);
                }
            }
        });

    }
}
