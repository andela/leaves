package com.worldtreeinc.leaves;

import android.content.Context;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by kamiye on 7/15/15.
 */
public class UserRegistration {

    static Context context;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    UserRegistration(Context context, String username, String email, String password, String confirmPassword) {
        UserRegistration.context = context;
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public void register() {
        ParseUser user = new ParseUser();

        String message;

        user.setUsername(this.username);
        user.setPassword(this.password);// verify that password matches first
        user.setEmail(this.email);

        if (this.password.equals(this.confirmPassword)) {
            message = "Password does not match the Confirm password!";
            Toast.makeText(UserRegistration.context, message, Toast.LENGTH_LONG).show();
        }
        else {
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                        String message = "Sign Up Successful";
                        Toast.makeText(UserRegistration.context, message, Toast.LENGTH_LONG).show();
                    } else {
                        // get error message from parse
                        String message = e.getMessage();
                        Toast.makeText(UserRegistration.context, message, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
