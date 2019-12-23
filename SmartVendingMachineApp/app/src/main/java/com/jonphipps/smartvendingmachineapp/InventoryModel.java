package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class InventoryModel implements Serializable { //Class to model the base and topping items
    private int id = 0;
    private String name = "";
    private double cost = 0;
    private int stock = 0;
    private boolean selected = false;
    private int numberSold = 0;

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

    public double getCost() {
        return this.cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getStock() {
        return this.stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean getSelected() {
        return this.selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getNumberSold() {
        return numberSold;
    }

    public void setNumberSold(int numberSold) {
        this.numberSold = numberSold;
    }
}
