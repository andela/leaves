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

    // for password, only check for equality in order to protext password
    public boolean checkPasswordEquality() {
        return (this.password.equals(this.confirmPassword));
    }

    //set toast message function as it is used repeatedly
    public void setToastMessage (String message) {
        Toast.makeText(UserRegistration.context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Run local validity checks before moving over to parse
     */

    // verify email
    public boolean verifyEmail() {
        return  (!this.email.equals(""));
    }

    // check of password field is empty
    public boolean checkEmptyPassword() {
        return  (!this.password.equals(""));
    }


    public void register() {

        // define new parse user object
        ParseUser user = new ParseUser();

        // set the user properties
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(this.email);

        // define message variable
        String message;

        // check if email field is not empty
        if (!verifyEmail()) {
            message = "Email cannot be missing or blank";
            setToastMessage(message);
        }
        else if (!checkEmptyPassword()) {
            message = "Password cannot be missing or blank";
            setToastMessage(message);
        }
        // check if password fields do not match
        else if (!checkPasswordEquality()) {
            message = "Password does not match the confirm password!";
            setToastMessage(message);
        }
        // let parse handle the remaining validation and register user
        else {
            UserRegistration.loader.start();
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                        String message = "Sign Up Successful";
                        UserRegistration.loader.stop();
                        setToastMessage(message);

                        ParseUser.logInInBackground(username, password, new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // start new activity when login is successful
                                    Intent intent = new Intent(UserRegistration.context, RoleActivity.class); // RoleOptionActivity.class - the real class name
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    UserRegistration.context.startActivity(intent);
                                } else {
                                    // change activity to login if sign up was successful but login failed
                                    Intent intent = new Intent(UserRegistration.context, MainActivity.class); // LoginActivity.class - from kingsley
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
}
