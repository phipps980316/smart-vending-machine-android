package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminControlPanel extends UserControlPanel implements Serializable { //Class the represent the admin control panel for the user to manage the machine

    public AdminControlPanel(UserModel user) { //class constructor that calls the superclass constructor
        super(user);
    }

    public void updateUser(UserModel user) { //calls the database function to update a user
        databaseUtility.updateUser(user);
    }

    public void deleteUser(UserModel user) { //calls the database function to delete a user
        databaseUtility.deleteUser(user);
    }

    public ArrayList<UserModel> getAllUsers() { //calls the database function to get all users and returns the list of user models
        return databaseUtility.getAllUsers();
    }

    public boolean addBase(InventoryModel base) { //checks if the base name is taken and if the base name is not taken, calls the database function to add a base and return true, else return false
        if(!databaseUtility.checkNameTaken(DatabaseUtility.TABLE_BASES, base.getName())) {
            databaseUtility.addInventory(DatabaseUtility.TABLE_BASES,base);
            return true;
        }
        else return false;
    }

    public void updateBase(InventoryModel base) { //calls the database function to update a base
        databaseUtility.updateInventory(DatabaseUtility.TABLE_BASES,base);
    }

    public void deleteBase(InventoryModel base) { //calls the database function to delete a base
        databaseUtility.deleteInventory(DatabaseUtility.TABLE_BASES, DatabaseUtility.TABLE_BASEORDERS, base);
    }

    public ArrayList<InventoryModel> getAllBases() { //calls the database function to get all bases and returns the list of inventory models
        return databaseUtility.getAllInventory(DatabaseUtility.TABLE_BASES);
    }

    public boolean addTopping(InventoryModel topping) { //checks if the topping name is taken and if the topping name is not taken, calls the database function to add a topping and return true, else return false
        if(!databaseUtility.checkNameTaken(DatabaseUtility.TABLE_TOPPINGS, topping.getName())) {
            databaseUtility.addInventory(DatabaseUtility.TABLE_TOPPINGS,topping);
            return true;
        }
        else return false;
    }

    public void updateTopping(InventoryModel topping) { //calls the database function to update a topping
        databaseUtility.updateInventory(DatabaseUtility.TABLE_TOPPINGS,topping);
    }

    public void deleteTopping(InventoryModel topping) { //calls the database function to delete a topping
        databaseUtility.deleteInventory(DatabaseUtility.TABLE_TOPPINGS, DatabaseUtility.TABLE_TOPPINGORDERS, topping);
    }

    public ArrayList<InventoryModel> getAllToppings() { //calls the database function to get all toppings and returns the list of inventory models
        return databaseUtility.getAllInventory(DatabaseUtility.TABLE_TOPPINGS);
    }

    public int getTotalNumberOfUsers() { //calls the database function to get the number of users in the database and returns that value
        return databaseUtility.getUserCount();
    }

    public int getTotalNumberOfOrders() { //calls the database function to get the number of orders in the database and returns that value
        return databaseUtility.getOrderCount();
    }

    public double getTotalOrderAmount() { //calls the database function to get the total amount of orders in the database and returns that value
        return databaseUtility.getTotalOrderAmount();
    }

    public ArrayList<InventoryModel> getOutOfStockBases() { //calls the database function to get all out of stock bases and returns the list of inventory models
        return databaseUtility.getOutOfStockItems(DatabaseUtility.TABLE_BASES);
    }

    public ArrayList<InventoryModel> getOutOfStockToppings() { //calls the database function to get all out of stock toppings and returns the list of inventory models
        return databaseUtility.getOutOfStockItems(DatabaseUtility.TABLE_TOPPINGS);
    }

    public ArrayList<InventoryModel> getBasesSold() { //calls the database function to get all base sales data and returns the list of inventory models
        return databaseUtility.getBasesSold();
    }

    public ArrayList<InventoryModel> getToppingsSold() { //calls the database function to get all topping sales data and returns the list of inventory models
        return databaseUtility.getToppingsSold();
    }
}