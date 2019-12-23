package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class VendingMachine implements Serializable { //Class to model the vending machine and the main class of the program
    private UserModel user; //Object to hold the user's details
    private ControlPanelResourceFactory controlPanelResourceFactory = new ControlPanelResourceFactory(); //Object to create control panel objects
    private DispenserResourceFactory dispenserResourceFactory = new DispenserResourceFactory(); //Object to create dispenser objects
    private LoginManager loginManager = new LoginManager(); //Object to manage login and register operations
    private boolean validUser = false; //Flag to signal if a valid user is signed into the machine

    public boolean login(String username, String password){ //Function to manage user logins
        user = loginManager.authenticateUser(username, password);
        if (user == null) validUser = false;
        else validUser = true;
        return validUser;
    }

    public void logout() { //Function to manage user logouts
        user = null;
        validUser = false;
    }

    public void refreshUser(){ //Function to refresh the user's details
        user = loginManager.refreshUser(user);
    }

    public boolean registerUser(String username, String password){ //Function to register new users
        return loginManager.registerUser(username, password);
    }

    public Dispenser accessDispenser() { //Function to request a dispenser object
        if(validUser) return dispenserResourceFactory.accessResource(user);
        else return null;
    }

    public UserControlPanel accessControlPanel() { //Function to request a control panel object
        if(validUser) return controlPanelResourceFactory.accessResource(user);
        else return null;
    }
}
