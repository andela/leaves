package com.worldtreeinc.leaves;


import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.EditText;

import com.rey.material.widget.Button;
import com.worldtreeinc.leaves.activity.RegisterActivity;

/**
 * Created by kamiye on 7/22/15.
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

    private RegisterActivity mActivity;
    private EditText mEditTextUsername;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditTextConfirmPassword;
    private Button signUpBtn;

    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();

        // reference the UI elements
        mEditTextUsername = (EditText) mActivity.findViewById(R.id.register_username);
        mEditTextEmail = (EditText) mActivity.findViewById(R.id.register_email);
        mEditTextPassword = (EditText) mActivity.findViewById(R.id.register_password);
        mEditTextConfirmPassword = (EditText) mActivity.findViewById(R.id.register_confirm_password);

        //username editTest test
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mEditTextUsername.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("username");

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mEditTextEmail.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("email@mail.com");

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mEditTextPassword.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("password");

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mEditTextConfirmPassword.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("password");

        signUpBtn = (Button) mActivity.findViewById(R.id.register_signup_button);
        TouchUtils.clickView(this, signUpBtn);

    }

    // preconditions
    public void testPreconditions() {
        assertNotNull("mActivity is null", mActivity);
        assertNotNull("EditTests might be null", mEditTextUsername);
    }

    // test all four EditText views
    public void testEditUsername() {
        String actual = mEditTextUsername.getText().toString();
        assertEquals("Error in EditText", "username", actual);
    }

    public void testEditEmail() {
        String actual = mEditTextEmail.getText().toString();
        assertEquals("Error in EditText", "email@mail.com", actual);
    }

    public void testEditPassword() {
        String actual = mEditTextPassword.getText().toString();
        assertEquals("Error in EditText", "password", actual);
    }

    public void testEditConfirmPassword() {
        String actual = mEditTextConfirmPassword.getText().toString();
        assertEquals("Error in EditText", "password", actual);
    }

    public void testSignUpButton() {
        //signup button test
        String actual = signUpBtn.getText().toString();
        assertEquals("Error in Button", "Sign Up", actual);
    }
}
