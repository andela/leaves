package com.worldtreeinc.leaves.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.worldtreeinc.leaves.R;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText resetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        setUp();
    }

    private void setUp() {
        resetEmail = (EditText) findViewById(R.id.resetPasswordTextBox);
        TextView reset_password_button = (TextView) findViewById(R.id.resetPasswordButton);
        reset_password_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.resetPasswordButton) {
            resetPassword();
        }
    }

    public void resetPassword(){
        String email =  resetEmail.getText().toString().trim();
        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // An email was successfully sent with reset instructions.
                    switchToLogin();
                } else {
                    // Something went wrong. Look at the ParseException to see what's up.
                    System.out.println("Something went wrong. Look at the ParseException to see what's up.");
                    e.printStackTrace();
                }
            }
        });
    }

    public void switchToLogin() {
        // switch to the login activity after the password reset link has been sent
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
