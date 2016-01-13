package com.worldtreeinc.leaves.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.appConfig.AppState;
import com.worldtreeinc.leaves.model.User;
import com.worldtreeinc.leaves.utility.ActivityLauncher;

public class RoleOptionActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoggedInUser();
        setContentView(R.layout.activity_role_option);
        setUpButtons();
    }

    private void setUpButtons() {
        TextView logout = (TextView) findViewById(R.id.logout);
        Button plannerBtn = (Button) findViewById(R.id.plannerBtn);
        Button bidderBtn = (Button) findViewById(R.id.bidderBtn);

        logout.setOnClickListener(this);
        plannerBtn.setOnClickListener(this);
        bidderBtn.setOnClickListener(this);
    }

    private void checkLoggedInUser() {
        if (!User.isLoggedIn()) {
            Intent getStartedIntent = new Intent(this, GetStartedActivity.class);
            startActivity(getStartedIntent);
        }
    }

    @Override
    public void onBackPressed() {
        AppState.minimize(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.plannerBtn:
                ActivityLauncher.runIntent(this, PlannerDashActivity.class);
                break;
            case R.id.bidderBtn:
                ActivityLauncher.runIntent(this, BidderDashActivity.class);
                break;
            case R.id.logout:
                User.logout();
                ActivityLauncher.runIntent(this, WelcomeActivity.class);
                break;
        }
    }

}