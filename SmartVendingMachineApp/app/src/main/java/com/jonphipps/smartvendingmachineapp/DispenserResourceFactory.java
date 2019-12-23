package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class DispenserResourceFactory implements Serializable { //Class to produce objects of type control panel
    private Permission permission = new Permission(); // Object of type permission

    public Dispenser accessResource(UserModel user) { //Function checks the permissions of the user and returns the a dispenser object if the user has the correct permissions
        final int resourceID = 0;
        if (permission.checkPermissions(resourceID, user.getRole())) return new Dispenser(user);
        else return null;
    }
}
