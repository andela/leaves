package com.worldtreeinc.leaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rey.material.widget.TextView;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener{
    private EditText mUsername;
    private EditText mPassword;
    private Button loginButton;
    private TextView registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialise();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){

                case R.id.loginButton:
                    String username =mUsername.getText().toString().trim();
                    String password =mPassword.getText().toString().trim();
                    UserAuth userAuthentication = new UserAuth(LoginActivity.this, username, password);
                    userAuthentication.login();
                    break;
                case R.id.registerUser:
                    Intent register = new Intent(this, RegisterActivity.class);
                    startActivity(register);
                    break;
            }

        }catch(Exception e){

        }
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
    private void initialise() {
        mUsername = (EditText) findViewById(R.id.usernameLoginTextBox);
        mPassword = (EditText) findViewById(R.id.passwordLoginTextBox);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerUser = (TextView) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }
}
