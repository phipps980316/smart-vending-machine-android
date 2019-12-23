package com.jonphipps.smartvendingmachineapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginManagerTest {
    LoginManager loginManager = new LoginManager();

    @Test
    public void correctLogin(){
        assertNotNull(loginManager.authenticateUser("admin", "admin2"));
    }

    @Test
    public void incorrectLogin() {
        assertNull(loginManager.authenticateUser("admin", "admin"));
        assertNull(loginManager.authenticateUser("jonathan", "123"));
    }

    @Test
    public void registerNewUser() {
        assertTrue(loginManager.registerUser("bill", "123"));
    }

    @Test
    public void registerNonUniqueName() {
        assertFalse(loginManager.registerUser("admin", "admin"));
    }
}