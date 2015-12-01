package com.worldtreeinc.leaves.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.parse.ParseFacebookUtils;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.helper.UserAuthentication;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialise();
        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseFacebookUtils.initialize(this.getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        try {
            String username = mUsername.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            UserAuthentication userAuthentication = new UserAuthentication(LoginActivity.this, username, password);
            switch (v.getId()){
                case R.id.loginButton:
                   userAuthentication.login();
                    break;
                case R.id.registerUser:
                    Intent register = new Intent(this, RegisterActivity.class);
                    startActivity(register);
                    break;
                case R.id.FacebookLoginButton:
                    userAuthentication.FacebookLogin();
                    break;
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void initialise() {
        mUsername = (EditText) findViewById(R.id.usernameLoginTextBox);
        mPassword = (EditText) findViewById(R.id.passwordLoginTextBox);

        TextView registerUser = (TextView) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        Button facebookLoginButton = (Button) findViewById(R.id.FacebookLoginButton);
        facebookLoginButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

}
