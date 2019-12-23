package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class StateSelectRecommendations extends State implements Serializable { //Class to represent the select recommendations state of the vending machine
    public StateSelectRecommendations(Dispenser context) {
        super(context);
    }

    @Override
    public void selectRecommendation(InventoryModel recommendation) { //State transition to allow the user to select a recommended topping to add to their product
        if(recommendation == null){
            context.setState(State.PRODUCT_SUMMARY);
        }
        else {
            Topping selectedTopping = new Topping(recommendation.getName(), recommendation.getCost());
            selectedTopping.addToProduct(context.getItemToDispense());
            context.setItemToDispense(selectedTopping);
            context.setSelectedTopping(recommendation);
        }
    }
}
