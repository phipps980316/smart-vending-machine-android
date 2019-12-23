package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class StateProductSummary extends State implements Serializable { //Class to represent the product summary state of the vending machine
    public StateProductSummary(Dispenser context){ super(context);}

    public void confirmation(boolean confirm){ //State transition that asks the user to confirm the item that they have selected
        if(confirm){
            if(context.getItemToDispense().getProductCost() > context.getUser().getCredit()){
                context.setState(State.INSERT_CREDIT);
            }
            else{
                context.setState(State.DISPENSE_ITEM);
            }
        }
        else{
            context.restartDispenser();
            context.setState(State.SELECT_BASE);
        }
    }
}
