package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;

import org.json.JSONObject;


public class MainActivity extends Activity {

    View view;
    private Bundle extras;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        extras = intent.getExtras();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // check if user is logged in
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (extras != null) {
                    changeToEventDetails();
                } else if (currentUser != null) {
                    // call method to change activity to RoleOptionActivity
                    changeToRoleOption(view);
                } else {
                    // call method to change activity to GetStartedActivity
                    changeToGetStarted(view);
                }

            }
        }, 3000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            Log.i("TAG", jsonData);
            JSONObject object = new JSONObject(jsonData);
            Intent newIntent = new Intent(this, EventDetailsActivity.class);
            newIntent.putExtra("OBJECT_ID", object.getString("eventId"));
            newIntent.putExtra("IS_PLANNER", false);
            startActivity(newIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
