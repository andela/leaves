package com.worldtreeinc.leaves.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.appConfig.AppState;


public class WelcomeActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setUpButtons();
    }

    public void setUpButtons() {
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent registerIntent;
        switch(view.getId()) {
            case R.id.registerBtn:
                registerIntent = new Intent(this, RegisterActivity.class);
                break;
            case R.id.loginBtn:
                registerIntent = new Intent(this, LoginActivity.class);
                break;
            default:
                return;
        }
        startActivity(registerIntent);
    }
    @Override
    public void onBackPressed() {
        AppState.minimize(this);
    }

}
