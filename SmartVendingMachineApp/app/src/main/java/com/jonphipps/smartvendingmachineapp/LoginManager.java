package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class LoginManager implements Serializable { //Class to manage the login and register options of the machine
    private DatabaseUtility databaseUtility = DatabaseUtility.getInstance(); //database connection object

    public UserModel authenticateUser(String username, String password) { //Function to check the hash value of the given details against the hash value associated with the username
        UserModel userModel = databaseUtility.getUser(username);
        if(userModel != null) {
            if(MD5Hash(password, userModel.getSalt()).equals(userModel.getHash())) return userModel;
            else return null;
        }
        else return null;
    }

    public UserModel refreshUser(UserModel user) { //Function to refresh the user's details
        return databaseUtility.getUser(user.getName());
    }

    public boolean registerUser(String username, String password) { //Function to register a new user for the machine
        UserModel newUser =  new UserModel();
        newUser.setName(username);
        newUser.setSalt(generateSalt());
        newUser.setHash(MD5Hash(password, newUser.getSalt()));
        newUser.setCredit(0);
        if(databaseUtility.getUserCount() == 0) newUser.setRole("admin");
        else newUser.setRole("user");

        if(!databaseUtility.checkNameTaken(DatabaseUtility.TABLE_USERS, username)) {
            databaseUtility.newUser(newUser);
            return true;
        }
        else return false;
    }

    private String generateSalt() { //Function to generate a random 64 character salt value for the user
        char[] chars = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                '1','2','3','4','5','6','7','8','9','0','!','£','$','%','&','*','@','#','~','?'};
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < 64; i++) {
            stringBuilder.append(String.valueOf(chars[random.nextInt(chars.length)]));
        }
        return stringBuilder.toString();
    }

    private String MD5Hash(String message, String salt){ //Function to encrypt the user's password + salt value
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

