package com.worldtreeinc.leaves;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

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
                    //Intent loginToast = new Intent(activity, LoginToast.class);
                    //activity.startActivity(loginToast);
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage(e.getMessage());
                    builder.setTitle("Sorry!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

}
