package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class Product implements Serializable { //Class to represent a product from the machine
    protected String productName;
    protected double productCost;

    Product(String name, double cost) {
        this.productName = name;
        this.productCost = cost;
    }

    public String getProductName() {
        return this.productName;
    }
    public double getProductCost() {
        return this.productCost;
    }
}
