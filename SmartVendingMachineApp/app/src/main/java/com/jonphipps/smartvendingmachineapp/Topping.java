package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;

public class Topping extends Product implements Serializable { //Class to represent the toppings of products and forms the basis of the decorator pattern
    private Product product;

    public Topping(String name, double cost) {
        super(name, cost);
    }

    public void addToProduct(Product existingProduct) {
        product = existingProduct;
    }

    @Override
    public String getProductName() {
        return product.getProductName() + " + " + this.productName;
    }

    @Override
    public double getProductCost() {
        return product.getProductCost() + this.productCost;
    }
}
