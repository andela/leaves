package com.worldtreeinc.leaves;


import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

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
    }

    // preconditions
    public void testPreconditions() {
        assertNotNull("mActivity is null", mActivity);
        assertNotNull("EditTests might be null", mEditTextUsername);
    }

    // test all four EditText views
    public void testEditTexts() {
        testText("Username", "Username", mEditTextUsername);
        testText("Email", "Email", mEditTextEmail);
        testText("password", "password", mEditTextPassword);
        testText("confirmpassword", "confirmpassword", mEditTextConfirmPassword);
    }

    public void testText (String setText, String expected, EditText editText ) {
        editText.setText(setText, TextView.BufferType.EDITABLE);

        String actual = editText.getText().toString();
        assertEquals("Error in EditText", expected, actual);
    }

    public void testSignUpButton() {
        //signup button test
        signUpBtn = (Button) mActivity.findViewById(R.id.register_signup_button);

        TouchUtils.clickView(this, signUpBtn);
    }
}
