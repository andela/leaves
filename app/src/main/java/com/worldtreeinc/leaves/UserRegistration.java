package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.EditText;
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

    private ProgressView loader;
    private Activity activity;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    // Error constants
    public static final int NO_ERROR = 0;
    public static final int NO_EMAIL = 1;
    public static final int NO_PASSWORD = 2;
    public static final int UNMATCHED_PASSWORD = 3;

    public UserRegistration (Activity activity) {
        if (activity != null) {
            this.activity = activity;
            setParameters();
            setLoader();
        }
    }

    // set parameters from the edit text field
    private void setParameters() {
        // check if activity is null
        if (this.activity != null) {
            // assign user inputs to variables
            EditText username = (EditText) activity.findViewById(R.id.register_username);
            EditText email = (EditText) activity.findViewById(R.id.register_email);
            EditText password = (EditText) activity.findViewById(R.id.register_password);
            EditText confirmPassword = (EditText) activity.findViewById(R.id.register_confirm_password);
            // convert variables to strings
            this.username = username.getText().toString();
            this.email = email.getText().toString();
            this.password = password.getText().toString();
            this.confirmPassword = confirmPassword.getText().toString();
        }
        else {
            // set parameters to empty strings if activity is null
            this.username = "";
            this.email = "";
            this.password = "";
            this.confirmPassword = "";
        }

    }

    // create external setter method for all parameters
    public void editParameters(String username, String email, String password, String confirmPassword) {
        // set parameters if method is called manually
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // set loader method
    // activity must be set to be able to use this method
    private boolean setLoader() {
        if (this.activity != null) {
            loader = (ProgressView) activity.findViewById(R.id.loading);
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

    public int isValid() {
        // for password, only check for equality in order to protext password

        if (this.email.equals("")) {
            return NO_EMAIL;
        }

        if (this.password.equals("")) {
            return NO_PASSWORD;
        }
        else if (!this.password.equals(this.confirmPassword)) {
            return UNMATCHED_PASSWORD;
        }

        return NO_ERROR;
    }

    private void toastNotification(int error_code) {
        String message = "";
        switch (error_code) {
            case NO_EMAIL:
                message += "Email field must not be empty or blank";
                break;
            case NO_PASSWORD:
                message += "Password field must not be empty or blank";
                break;
            case UNMATCHED_PASSWORD:
                message += "Password field does not match confirm password";
                break;
            default:
                message += "Unknown error occured. Please fill the form properly and try again";
        }

        setToastMessage(message);
    }

    //set toast message function as it is used repeatedly
    private void setToastMessage (String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Run local validity checks before moving over to parse
     */



    private void parseRegister() {

        // define new parse user object
        ParseUser user = new ParseUser();

        // set the user properties
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(this.email);

        // let parse handle the remaining validation and register user
        loader.start();
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    String message = "Sign Up Successful";
                    loader.stop();
                    setToastMessage(message);
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            Class activitySwitch;
                            if (user != null) {
                                activitySwitch = RoleOptionActivity.class;
                            } else {
                                activitySwitch = LoginActivity.class;
                            }
                            // start new activity
                            Intent intent = new Intent(activity, activitySwitch);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
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
                    loader.stop();
                    setToastMessage(message);
                }
            }
        });

    }

    public void register() {
        // run initial local validation
        int localValidation = isValid();

        // after all is done, register the user with parse
        if (localValidation == NO_ERROR) {
            parseRegister();
        }
        else {
            toastNotification(localValidation);
        }
    }
}