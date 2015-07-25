package com.worldtreeinc.leaves;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    public void testActivityExists() {
        LoginActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testGreet() {
        LoginActivity activity = getActivity();

        //username editTest test
        final EditText nameEditText = (EditText) activity.findViewById(R.id.usernameLoginTextBox);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                nameEditText.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("username");

        final EditText passwordEditText = (EditText) activity.findViewById(R.id.passwordLoginTextBox);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                nameEditText.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("password");

        //login button test
        Button LoginButton = (Button) activity.findViewById(R.id.loginButton);
        TouchUtils.clickView(this, LoginButton);

        //Facebook login button test
        Button FacebookLoginButton = (Button) activity.findViewById(R.id.FacebookLoginButton);
        TouchUtils.clickView(this, FacebookLoginButton);

        //app name test
        TextView AppName = (TextView) activity.findViewById(R.id.textView);
        String actualText = AppName.getText().toString();
        assertEquals("PROJECT NAME", actualText);
    }
}
