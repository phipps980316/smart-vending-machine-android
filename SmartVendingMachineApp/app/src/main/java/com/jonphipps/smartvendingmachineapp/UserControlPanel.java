package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class UserControlPanel implements Serializable { //Class the represent the user control panel for the user to manage the machine
    private UserModel user; //Object to hold the user's details
    protected DatabaseUtility databaseUtility = DatabaseUtility.getInstance(); //database connection object

    public UserControlPanel(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public void updateAccount() { //Function to refresh the user's details from the database
        databaseUtility.updateUser(user);
    }

    public ArrayList<OrderRecordModel> getUserOrderHistory(){ //Function to get all of the order history for a given user from the database
        return databaseUtility.getUserOrders(user);
    }

    public String MD5Hash(String message, String salt){ //Function to encrypt the user's password + salt value
        String md5 = "";
        if(message == null) return null;

        message = message+salt;
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(message.getBytes(), 0, message.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e){

        }
        return md5;
    }
}
