package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class ControlPanelResourceFactory implements Serializable { //Class to produce objects of type control panel
    private Permission permission = new Permission(); // Object of type permission

    public UserControlPanel accessResource(UserModel user) { //Function checks the permissions of the user and returns the relevant control panel object
        final int adminResourceID = 1;
        final int userResourceID = 2;
        if (permission.checkPermissions(adminResourceID, user.getRole())) return new AdminControlPanel(user);
        else if(permission.checkPermissions(userResourceID, user.getRole())) return new UserControlPanel(user);
        else return null;
    }
}
