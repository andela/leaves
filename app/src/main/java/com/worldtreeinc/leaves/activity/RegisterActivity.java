package com.worldtreeinc.leaves.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.helper.UserAuthentication;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton();
    }

    private void registerButton() {
        TextView register_login_button = (TextView) findViewById(R.id.register_login_button);
        register_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void register(View view) {
        // instantiate register object
        UserAuthentication registerObject = new UserAuthentication(this);
        // call register method
        registerObject.register();
    }

    public void switchToLogin() {
        // switch to the login activity if the login button is clicked
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}