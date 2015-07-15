package com.worldtreeinc.leaves;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
     * @param view - Calls the register method from the UserRegistration class
     *             and registers the user
     */
    public void register(View view) {

        // assign user inputs to variables
        EditText username = (EditText) findViewById(R.id.register_username);
        EditText email = (EditText) findViewById(R.id.register_email);
        EditText password = (EditText) findViewById(R.id.register_password);
        EditText confirmPassword = (EditText) findViewById(R.id.register_confirm_password);

        // convert variables to strings
        String stringUsername = username.getText().toString();
        String stringEmail = email.getText().toString();
        String stringPassword = password.getText().toString();
        String stringConfirmPassword = confirmPassword.getText().toString();

        // access the user registration class and call the register method
        UserRegistration registerObject = new UserRegistration(getApplicationContext(), stringUsername, stringEmail, stringPassword, stringConfirmPassword);
        registerObject.register();

    }
}