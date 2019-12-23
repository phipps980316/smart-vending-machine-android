package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;
import java.text.DecimalFormat;

public class StateDispenseItem extends State implements Serializable { //Class to represent the dispense item state of the vending machine

    public StateDispenseItem(Dispenser context) {
        super(context);
    }

    @Override
    public void dispense() { //State transition to dispense the current item and update the user's account and the inventory stock then set the state to select base
        UserModel user = context.getUser();
        InventoryModel selectedBase = context.getSelectedBase();
        InventoryModel selectedTopping;

        DecimalFormat currency = new DecimalFormat("0.00");
        user.setCredit(Double.valueOf(currency.format(user.getCredit() - context.getItemToDispense().getProductCost())));
        context.updateUser(user);

        selectedBase.setStock(selectedBase.getStock() - 1);
        context.updateBase(selectedBase);
        context.recordBaseSale(selectedBase);

        for (int position = 0; position < context.getSizeOfSelectedToppings(); position++) {
            selectedTopping = context.getSelectedTopping(position);
            int toppingInventory = selectedTopping.getStock();
            selectedTopping.setStock(toppingInventory - 1);
            context.updateTopping(selectedTopping);
            context.recordToppingSale(selectedTopping);
        }
        context.recordOrder();
        context.setItemToDispense(null);
        context.setState(SELECT_BASE);
    }
}
