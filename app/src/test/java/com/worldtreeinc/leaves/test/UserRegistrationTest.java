package com.worldtreeinc.leaves.test;


import com.worldtreeinc.leaves.UserAuthentication;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kamiye on 7/22/15.
 */
public class UserRegistrationTest {

    // set initial conditions
    String username = "username";
    String email = "email@mail.com";
    String password = "password";
    String passwordAgain = "password";

    UserAuthentication registerObject = mock(UserAuthentication.class);

    @Mock
    UserAuthentication getRegisterObject;

    @Before
    public void setInitialConditions() {
        Mockito.doCallRealMethod().when(registerObject).editParameters(username, email, password, passwordAgain);
    }

    @Test
    public void verifyEditParameters() {
        registerObject.editParameters(username, email, password, passwordAgain);
        verify(registerObject).editParameters(username, email, password, passwordAgain);
    }

    @Test
    public void verifyGetUsername() {
        String expected = "username";
        when(registerObject.getUsername()).thenReturn(username);

        assertEquals("Username getter not functioning", expected, username);
    }
    @Test
    public void verifyGetEmail() {
        String expected = "email@mail.com";
        when(registerObject.getEmail()).thenReturn(email);

        assertEquals("Email getter not functioning", expected, email);
    }

    @Test
    public void verifyIsValid() {
        registerObject.isValid();
        verify(registerObject).isValid();
    }

    @Test
    public void verifyRegister() {
        registerObject.register();
        verify(registerObject).register();
    }



}