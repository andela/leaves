package com.worldtreeinc.leaves.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.worldtreeinc.leaves.UserRegistration;
/**
 * Created by kamiye on 7/21/15.
 */
public class UserRegistrationTest {
    // set initial configurations
    String username = "username";
    String email = "email@mail.com";
    String password = "password";
    String passwordAgain = "password";

    UserRegistration registerObject = new UserRegistration(username, email, password, passwordAgain);

    // test getter methods
    @Test
    public void testGetUsername() {
        String expected = username;
        String actual = registerObject.getUsername();

        assertEquals("Username getter failed", expected, actual);
    }

    @Test
    public void testGetEmail() {
        String expected = email;
        String actual = registerObject.getEmail();
        assertEquals("Username getter failed", expected, actual);
    }

    // text localised validations

    @Test
    public void testCorrectInputs() {
        int expected = 0;
        int result = registerObject.isValid();

        assertEquals("Correct input result failed", expected, result);
    }

    @Test
    public void testEmptyEmail() {
        email = "";
        UserRegistration registerObject = new UserRegistration(username, email, password, passwordAgain);

        int expected = 1;

        int result = registerObject.isValid();

        assertEquals("Equal password check not functional", expected, result);
    }

    @Test
    public void testEmptyPassword() {
        password = "";
        UserRegistration registerObject = new UserRegistration(username, email, password, passwordAgain);

        int expected = 2;

        int result = registerObject.isValid();

        assertEquals("Equal password check not functional", expected, result);
    }

    @Test
    public void testNoneEqualPassword() {
        passwordAgain = "passwordAgain";
        UserRegistration registerObject = new UserRegistration(username, email, password, passwordAgain);
        int expected = 3;

        int result = registerObject.isValid();

        assertEquals("Equal password check not functional", expected, result);
    }
}
