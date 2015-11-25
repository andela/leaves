package com.worldtreeinc.leaves.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.parse.ParseAnalytics;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.model.User;

import org.json.JSONObject;


public class MainActivity extends Activity {

    private View view;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        extras = getIntent().getExtras();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // check if user is logged in
                if (extras != null) {
                    changeToEventDetails();
                } else if (User.isLoggedIn()) {
                    // call method to change activity to RoleOptionActivity
                    changeToRoleOption(view);
                } else {
                    // call method to change activity to GetStartedActivity
                    changeToGetStarted(view);
                }

            }
        }, 3000);

    }

    // method to move to the GetStarted Activity
    public void changeToGetStarted(View view) {
        // user is not logged in yet, change to GetStartedActivity
        Intent intent = new Intent(this, GetStartedActivity.class);
        startActivity(intent);
    }

    // method to move to RoleOption Activity
    public void changeToRoleOption(View view) {
        // change to the RoleOptionActivity
        Intent intent = new Intent(this, RoleOptionActivity.class);
        startActivity(intent);
    }

    public  void changeToEventDetails(){
        try {
            String jsonData = extras.getString("com.parse.Data");

            JSONObject object = new JSONObject(jsonData);
            Intent newIntent = new Intent(this, EventDetailsActivity.class);
            newIntent.putExtra(getString(R.string.object_id_reference), object.getString("eventId"));
            newIntent.putExtra(getString(R.string.is_planner_reference), false);
            startActivity(newIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
