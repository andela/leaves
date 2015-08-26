package com.worldtreeinc.leaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private TextView register_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_login_button = (TextView) findViewById(R.id.register_login_button);
        register_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    /**
     *
     * @param view - Calls the register method from the UserAuthentication class
     *             and registers the user
     */
    public void register(View view) {
        // instantiate register object
        UserAuthentication registerObject = new UserAuthentication(this);
        // call register method
        registerObject.register();
    }

    public void switchToLogin() {
        // switch to the login activity if the login button is clicked
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}