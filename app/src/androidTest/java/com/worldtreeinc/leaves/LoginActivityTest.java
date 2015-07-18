package com.worldtreeinc.leaves;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by andela on 7/18/15.
 */
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
        Button greetButton =
                (Button) activity.findViewById(R.id.loginButton);

        TouchUtils.clickView(this, greetButton);

        //app name test
        TextView AppName =
                (TextView) activity.findViewById(R.id.textView);

        String actualText = AppName.getText().toString();
        assertEquals("AVENT", actualText);

        //username symbole test
        TextView UsernameNotificaton =
                (TextView) activity.findViewById(R.id.usernameTextView);

        String actualText2 = UsernameNotificaton.getText().toString();
        assertEquals("Username", actualText2);


        //password symbole test
        TextView PasswordNotificaton =
                (TextView) activity.findViewById(R.id.textView3);

        String actualText3 = PasswordNotificaton.getText().toString();
        assertEquals("Password", actualText3);


        //dont have an account view test
        TextView SignUpNotificaton =
                (TextView) activity.findViewById(R.id.textView4);

        String actualText4 = SignUpNotificaton.getText().toString();
        assertEquals("Don't have an Account?", actualText4);


        //sign up view test
        TextView SignUpClick =
                (TextView) activity.findViewById(R.id.registerUser);

        String actualText5 = SignUpClick.getText().toString();
        assertEquals("Sign Up", actualText5);
    }
}
