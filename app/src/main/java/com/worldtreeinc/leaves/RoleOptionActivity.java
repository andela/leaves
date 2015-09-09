package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class RoleOptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if user is logged in
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            //redirect to get started page
            Intent getStartedIntent = new Intent(this, GetStartedActivity.class);
            startActivity(getStartedIntent);
        }

        setContentView(R.layout.activity_role_option);

        Button bidderBtn = (Button) findViewById(R.id.bidderBtn);
        bidderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirect to bidder dashboard
                Intent bidderDash = new Intent(RoleOptionActivity.this, BidderDashActivity.class);
                startActivity(bidderDash);
            }
        });
        Button plannerBtn = (Button) findViewById(R.id.plannerBtn);
        plannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirect to bidder dashboard
                Intent plannerDash = new Intent(RoleOptionActivity.this, PlannerDashActivity.class);
                startActivity(plannerDash);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppState.minimize(this);
    }
}