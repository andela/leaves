package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerBtn:
                Intent register = new Intent(this, RegisterActivity.class);
                startActivity(register);
                break;
            case R.id.loginBtn:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                break;
        }
    }
}
