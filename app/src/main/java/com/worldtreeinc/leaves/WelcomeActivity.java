package com.worldtreeinc.leaves;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class WelcomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onClick(View view) {
        Intent register = new Intent(this, WelcomeActivity.class);
        Intent login = new Intent(this, WelcomeActivity.class);
        /*Intent register = new Intent(this, RegisterActivity.class);
        Intent login = new Intent(this, LoginActivity.class);*/
        switch (view.getId()) {
            case R.id.registerBtn:
                startActivity(register);
                break;
            case R.id.loginBtn:
                startActivity(login);
                break;
        }
    }
}
