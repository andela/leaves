package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rey.material.widget.ProgressView;

/**
 * Created by kamiye on 7/15/15.
 */
public class UserRegistration {

    static ProgressView loader;
    static Activity activity;
    static Context context;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    UserRegistration(Context context, Activity activity, String username, String email, String password, String confirmPassword) {
        UserRegistration.activity = activity;
        UserRegistration.context = context;
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        loader = (ProgressView) UserRegistration.activity.findViewById(R.id.loading);

    }

    public void setToastMessage (String message) {
        Toast.makeText(UserRegistration.context, message, Toast.LENGTH_LONG).show();
    }

    public void register() {
        ParseUser user = new ParseUser();

        String message;

        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(this.email);

        // check if email field is not empty
        if (this.email.equals("")) {
            message = "Email cannot be missing or blank";
            setToastMessage(message);
        }
        // check if password fields do not match
        else if (!this.password.equals(this.confirmPassword)) {
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

                        // start new activity when login is successful
                        Intent intent = new Intent(UserRegistration.context, RoleOptionActivity.class); // RoleOptionActivity.class - the real class name
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        UserRegistration.context.startActivity(intent);
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
