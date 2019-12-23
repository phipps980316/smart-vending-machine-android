package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class StateSelectTopping extends State implements Serializable { //Class to represent the select topping state of the vending machine
    public StateSelectTopping(Dispenser context) {
        super(context);
    }

    @Override
    public void selectTopping(InventoryModel topping) { //State transition to allow the user to select topping to add to their product
        if(topping == null){
            context.setState(FIND_RECOMMENDATIONS);
        }
        else {
            Topping selectedTopping = new Topping(topping.getName(), topping.getCost());
            selectedTopping.addToProduct(context.getItemToDispense());
            context.setItemToDispense(selectedTopping);
            context.setSelectedTopping(topping);
        }
    }
}
