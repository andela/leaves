package com.worldtreeinc.leaves.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.worldtreeinc.leaves.appConfig.AppState;
import com.worldtreeinc.leaves.R;

public class GetStartedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        final Intent welcomePage = new Intent(this, WelcomeActivity.class);
        Button getStartedBtn = (Button) findViewById(R.id.get_started);
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(welcomePage);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppState.minimize(this);
    }
}
