package com.jonphipps.smartvendingmachineapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class DispenserTest {
    DatabaseUtility databaseUtility = DatabaseUtility.getInstance();
    UserModel currentUser = databaseUtility.getUser("admin");
    Dispenser dispenser = new Dispenser(currentUser);

    @Test
    public void incorrectRunningOfStateMachine() {


    }

}