package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends Activity {
    private TextView register_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_login_button = (TextView) findViewById(R.id.register_login_button);
        register_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });
    }

    /**
     *
     * @param view - Calls the register method from the UserRegistration class
     *             and registers the user
     */
    public void register(View view) {
        // instantiate register object
        UserRegistration registerObject = new UserRegistration(this);
        // call register method
        registerObject.register();
    }

    public void switchToLogin() {
        // switch to the login activity if the login button is clicked
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}