package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class State implements Serializable { //Class to represents the states of the state machine
    protected Dispenser context; //Object to store the context of the state machine

    //Static integers to keep track of the position of the states in the available states vector
    public static final int SELECT_BASE = 0;
    public static final int SELECT_TOPPING = 1;
    public static final int FIND_RECOMMENDATIONS = 2;
    public static final int SELECT_RECOMMENDATIONS = 3;
    public static final int PRODUCT_SUMMARY = 4;
    public static final int INSERT_CREDIT = 5;
    public static final int DISPENSE_ITEM = 6;

    public State(Dispenser context) { //Constructor sets the context of the state machine
        this.context = context;
    }

    //The 7 functions below are used to print out error message when invalid transitions are accessed
    public void selectBase(InventoryModel base) {
        System.out.println("Cannot Select Base In Current State");
    }
    public void selectTopping(InventoryModel topping) {
        System.out.println("Cannot Select Topping In Current State");
    }
    public void findRecommendations(){
        System.out.println("Cannot Select Recommendation In Current State");
    }
    public void selectRecommendation(InventoryModel recommendation) {
        System.out.println("Cannot Select Recommendation In Current State");
    }
    public void confirmation(boolean confirm) {
        System.out.println("Cannot Confirm Product In Current State");
    }
    public boolean insertCredit(double credit) {
        System.out.println("Cannot Select Topping In Current State");
        return false;
    }
    public void dispense() {
        System.out.println("Cannot Dispense In Current State");
    }
}
