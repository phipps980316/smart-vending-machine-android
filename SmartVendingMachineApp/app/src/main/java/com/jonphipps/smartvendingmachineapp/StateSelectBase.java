package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class StateSelectBase extends State implements Serializable { //Class to represent the select base state of the vending machine
    public StateSelectBase(Dispenser context) {
        super(context);
    }

    @Override
    public void selectBase(InventoryModel base) { //State transition to allow the user to select a base to add to their product
        context.setItemToDispense(new Product(base.getName(), base.getCost()));
        context.setSelectedBase(base);
        context.setState(SELECT_TOPPING);
    }
}

