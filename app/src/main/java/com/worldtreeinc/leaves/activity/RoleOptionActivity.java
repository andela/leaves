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
            //redirect to get started page
            Intent getStartedIntent = new Intent(this, GetStartedActivity.class);
            startActivity(getStartedIntent);
        }
    }

    @Override
    public void onBackPressed() {
        AppState.minimize(this);
    }

    @Override
    public void onClick(View view) {
        Intent destinationIntent;
        int id = view.getId();
        switch (id) {
            case R.id.plannerBtn:
                destinationIntent = new Intent(this, PlannerDashActivity.class);
                break;
            case R.id.bidderBtn:
                destinationIntent = new Intent(this, BidderDashActivity.class);
                break;
            case R.id.logout:
                User.logout();
                destinationIntent = new Intent(this, WelcomeActivity.class);
                break;
            default:
                return;
        }
        startActivity(destinationIntent);
        finish();
    }
}