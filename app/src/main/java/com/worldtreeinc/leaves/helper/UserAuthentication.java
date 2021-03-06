package com.worldtreeinc.leaves.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.rey.material.widget.ProgressView;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.activity.RoleOptionActivity;
import com.worldtreeinc.leaves.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamiye on 7/15/15.
 */
public class UserAuthentication {

  private ProgressView loader;
  private Activity activity;
  private String username;
  private String email;
  private String password;
  private String confirmPassword;
  private User user = new User();


  // ERROR CONSTANT ENUMS DECLARATION
  public enum UserError {
    NO_ERROR,
    NO_EMAIL,
    NO_PASSWORD,
    UNMATCHED_PASSWORD
  }


  public UserAuthentication(Activity activity, String... userData) {
    if (activity != null) {
      this.activity = activity;
      if (userData.length != 0) {
        this.username = userData[0];
        this.password = userData[1];
      } else {
        setParameters();
      }
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
      this.username = username.getText().toString().toLowerCase();
      this.email = email.getText().toString();
      this.password = password.getText().toString();
      this.confirmPassword = confirmPassword.getText().toString();
    } else {
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
    } else {
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

  public UserError isValid() {
    // for password, only check for equality in order to protext password
    if (this.email.equals("")) {
      return UserError.NO_EMAIL;
    }
    if (this.password.equals("")) {
      return UserError.NO_PASSWORD;
    } else if (!this.password.equals(this.confirmPassword)) {
      return UserError.UNMATCHED_PASSWORD;
    }

    return UserError.NO_ERROR;

  }

  private void toastNotification(UserError error) {
    String message = "";
    switch (error) {
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
  private void setToastMessage(String message) {
    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
  }

  private class RegisterAsyncTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
      String message = null;
      try {
        user.signUp();
      } catch (Exception e) {
        message = e.getMessage();
      }
      return message;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      user.setUsername(username);
      user.setPassword(password);
      user.setEmail(email);

      loader.start();
    }

    @Override
    protected void onPostExecute(String string) {
      super.onPostExecute(string);
      if (string == null) {
        String message = "Sign Up Successful";
        loader.stop();
        setToastMessage(message);
        // call login after registering
        login();
      } else {
        // get error message from parse
        setToastMessage(string);
        loader.stop();
      }
    }

  }

  public void login() {

    AsyncTask<Void, Void, String> LoginAsyncTask = new AsyncTask<Void, Void, String>() {

      @Override
      protected String doInBackground(Void... params) {
        String message = null;
        try {
          user.logIn(username, password);
        } catch (Exception e) {
          message = e.getMessage().toString();
        }
        return message;
      }

      @Override
      protected void onPreExecute() {
        super.onPreExecute();
        loader.start();
      }

      @Override
      protected void onPostExecute(String message) {
        super.onPostExecute(message);
        if (message == null) {
          // Hooray! The user is logged in.
          setToastMessage("You are logged in");
          Intent roleIntent = new Intent(activity, RoleOptionActivity.class);
          activity.startActivity(roleIntent);
        } else {
          // Login failed. Look at the ParseException to see what happened.
          setToastMessage(message);
          return;
        }
      }
    };
    LoginAsyncTask.execute();
  }

  public void register() {
    // run initial local validation
    UserError localValidation = isValid();

    // after all is done, register the user with parse
    if (localValidation == UserError.NO_ERROR) {
      new RegisterAsyncTask().execute();
    } else {
      toastNotification(localValidation);
    }
  }

  public void FacebookLogin() {
    List<String> permissions = new ArrayList<>();
    permissions.add("public_profile");
    loader.start();
    ParseFacebookUtils.logInWithReadPermissionsInBackground(activity, permissions, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException err) {
        processUser(user, "Your account has been created!", activity);
      }
    });
  }

  public void twitterLogin() {
    loader.start();
    ParseTwitterUtils.logIn(activity, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException err) {
        processUser(user, "Your account has been created", activity);
      }
    });
  }

  private void processUser(ParseUser user, String message2, Activity activity) {
    Class activitySwitch;
    loader.stop();
    if (user == null) {
      setToastMessage("Login failed");
      return;
    } else if (user.isNew()) {
      setToastMessage(message2);
      activitySwitch = RoleOptionActivity.class;
    } else {
      setToastMessage("You are logged in");
      activitySwitch = RoleOptionActivity.class;
    }
    Intent intent = new Intent(activity, activitySwitch);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    activity.startActivity(intent);
  }
}