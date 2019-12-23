package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class UserModel implements Serializable { //Class to model the user
    private int id;
    private String name;
    private String hash;
    private String salt;
    private String role;
    private double credit;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getCredit() {
        return this.credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}