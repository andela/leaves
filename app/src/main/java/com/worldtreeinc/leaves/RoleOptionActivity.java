package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.bidderBtn:
                //redirect to bidder dashboard
                Intent bidderDash = new Intent(this, BidderDashActivity.class);
                startActivity(bidderDash);
                break;

            case R.id.plannerBtn:
                //redirect to planner dashboard
                Intent plannerDash = new Intent(this, PlannerDashActivity.class);
                startActivity(plannerDash);
                break;
        }
    }

}