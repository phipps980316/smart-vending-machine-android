package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Permission implements Serializable { //Class to manage the permissions of the machine
    private Map<String, int[]> permissions = new HashMap<>(); //Map to store the different user permissions

    public Permission() { //Constructor to set the permissions in the map
        //0th Element = Dispenser
        //1st Element = AdminCP
        //2nd Element = UserCP
        final int[] adminPermissions = {1,1,0};
        final int[] userPermissions = {1,0,1};
        permissions.put("admin", adminPermissions);
        permissions.put("user", userPermissions);
    }

    public boolean checkPermissions(int resourceID, String role) { //function to check the user has the correct role for a resource
        int[] usersPermissions = permissions.get(role);
        if (usersPermissions[resourceID] == 1) return true;
        else return false;
    }
}
