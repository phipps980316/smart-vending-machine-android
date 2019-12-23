package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class Dispenser extends StateContext implements Serializable { //Class to represent the main dispenser component of the vending machine
    private Product itemToDispense = null; //Object to hold the item to dispense
    private InventoryModel selectedBase = null; //Object to hold the base selected by the user
    private Vector<InventoryModel> selectedToppings = new Vector<>(); //Vector to hold the toppings selected by the user
    private Vector<InventoryModel> recommendedItems = new Vector<>(); //Vector to hold all of the recommendations generated
    private UserModel user; //Object to hold the users details
    private DatabaseUtility databaseUtility = DatabaseUtility.getInstance(); //database connection object

    Dispenser(UserModel user) { //Constructor to populate the user details and initalise the available states of the state machine
        this.user = user;
        this.availableStates.add(new StateSelectBase(this));
        this.availableStates.add(new StateSelectTopping(this));
        this.availableStates.add(new StateFindRecommendations(this));
        this.availableStates.add(new StateSelectRecommendations(this));
        this.availableStates.add(new StateProductSummary(this));
        this.availableStates.add(new StateInsertCredit(this));
        this.availableStates.add(new StateDispenseItem(this));

        this.setState(State.SELECT_BASE);
    }

    //The 7 functions below are used to access the transitions for the state machine depending on the current state of the machine
    public void selectBase(InventoryModel base) {
        this.currentState.selectBase(base);
    }
    public void selectTopping(InventoryModel topping) {
        this.currentState.selectTopping(topping);
    }
    public void findRecommendations(){
        this.currentState.findRecommendations();
    }
    public void selectRecommendation(InventoryModel recommendation) {
        this.currentState.selectRecommendation(recommendation);
    }
    public void confirmation(boolean confirm) {
        this.currentState.confirmation(confirm);
    }
    public boolean insertCredit(double credit) {
        return this.currentState.insertCredit(credit);
    }
    public void dispense() {
        this.currentState.dispense();
    }

    public void setItemToDispense(Product product) {
        this.itemToDispense = product;
    }
    public Product getItemToDispense() {
        return this.itemToDispense;
    }

    public void setSelectedBase(InventoryModel base) {
        this.selectedBase = base;
    }
    public InventoryModel getSelectedBase() {
        return this.selectedBase;
    }

    public void setSelectedTopping(InventoryModel topping) {
        this.selectedToppings.add(topping);
    }
    public InventoryModel getSelectedTopping(int index) {
        return this.selectedToppings.get(index);
    }
    public int getSizeOfSelectedToppings() {
        return this.selectedToppings.size();
    }
    public boolean checkToppingIsAdded(InventoryModel topping){
        for(int index = 0; index < selectedToppings.size(); index++){
            if(selectedToppings.get(index).getId() == topping.getId()){
                return true;
            }
        }
        return false;
    }

    public void setRecommendation(InventoryModel recommendation) {
        this.recommendedItems.add(recommendation);
    }
    public InventoryModel getRecommendation(int index) {
        return this.recommendedItems.get(index);
    }
    public int getSizeOfRecommendations() {
        return this.recommendedItems.size();
    }

    public UserModel getUser() {
        return this.user;
    }

    public ArrayList<InventoryModel> getBasesInventoryList() { //gets all of the in stock bases from the database
        return databaseUtility.getInventoryList(DatabaseUtility.TABLE_BASES);
    }
    public ArrayList<InventoryModel> getToppingsInventoryList() { //gets all of the in stock toppings from the database
        return databaseUtility.getInventoryList(DatabaseUtility.TABLE_TOPPINGS);
    }

    public ArrayList<InventoryModel> getAllToppingsList() { //gets all of the toppings from the database
        return databaseUtility.getAllInventory(DatabaseUtility.TABLE_TOPPINGS);
    }

    public void updateUser(UserModel user) { //function calls the database function to update the user's details
        databaseUtility.updateUser(user);
    }
    public void updateBase(InventoryModel base) { //function calls the datbase function to update a base
        databaseUtility.updateInventory(DatabaseUtility.TABLE_BASES, base);
    }
    public void updateTopping(InventoryModel topping) { //function calls the database function to update a topping
        databaseUtility.updateInventory(DatabaseUtility.TABLE_TOPPINGS, topping);
    }
    public ArrayList<OrderRecordModel> getToppingSalesData(){ //function to fetch all of the topping sales data from the database
        return databaseUtility.getAllToppingSales();
    }

    public void recordBaseSale(InventoryModel inventoryModel) { //function to record a base item sale
        databaseUtility.recordItemSale(DatabaseUtility.TABLE_BASEORDERS, user, inventoryModel);
    }

    public void recordToppingSale(InventoryModel inventoryModel) { //function to record a topping item sale
        databaseUtility.recordItemSale(DatabaseUtility.TABLE_TOPPINGORDERS, user, inventoryModel);
    }

    public void restartDispenser(){ //function to reset the dispenser object
        itemToDispense = null;
        selectedBase = null;
        selectedToppings.clear();
        recommendedItems.clear();
    }

    public void recordOrder(){ //function to record a product sale
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(currentDate);
        databaseUtility.recordOrderHistory(user, itemToDispense, date);
    }
}
