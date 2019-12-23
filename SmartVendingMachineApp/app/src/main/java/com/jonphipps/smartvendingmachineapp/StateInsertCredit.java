package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class StateInsertCredit extends State implements Serializable { //Class to represent the insert credit state of the vending machine
    public StateInsertCredit(Dispenser context) {
        super(context);
    }

    @Override
    public boolean insertCredit(double credit) { //State transition to add credit to the user's account
        double newCredit = context.getUser().getCredit() + credit;

        if(newCredit >= context.getItemToDispense().getProductCost()) {
            context.getUser().setCredit(newCredit);
            context.setState(DISPENSE_ITEM);
            return true;
        }
        else return false;
    }
}
