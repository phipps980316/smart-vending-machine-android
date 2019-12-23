package com.jonphipps.smartvendingmachineapp;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AdminControlPanelTest {
    DatabaseUtility databaseUtility = DatabaseUtility.getInstance();
    UserModel currentUser = databaseUtility.getUser("admin");
    AdminControlPanel controlPanel = new AdminControlPanel(currentUser);


    @Test
    public void updateUser() {
        UserModel user = databaseUtility.getUser("jon");
        user.setRole("admin");
        controlPanel.updateUser(user);
        assertEquals(databaseUtility.getUser("jon").getRole(), "admin");
    }

    @Test
    public void deleteUser() {
        UserModel user = databaseUtility.getUser("jon");
        controlPanel.deleteUser(user);
        assertNull(databaseUtility.getUser("jon"));
    }

    @Test
    public void addNewBase() {
        InventoryModel base = new InventoryModel();
        base.setName("Thin & Chispy");
        base.setCost(2.50);
        base.setStock(4);
        controlPanel.addBase(base);

        ArrayList<InventoryModel> bases = databaseUtility.getAllInventory(DatabaseUtility.TABLE_BASES);
        assertTrue(bases.get(0).getName().equals(base.getName()));
    }

    @Test
    public void addExistingBase() {
        InventoryModel base = new InventoryModel();
        base.setName("Thin & Chispy");
        base.setCost(2.50);
        base.setStock(4);

        assertFalse(controlPanel.addBase(base));
    }


    @Test
    public void updateBase() {
        InventoryModel base;
        ArrayList<InventoryModel> bases = databaseUtility.getAllInventory(DatabaseUtility.TABLE_BASES);
        base = bases.get(0);
        base.setStock(5);
        controlPanel.updateBase(base);

        bases = databaseUtility.getAllInventory(DatabaseUtility.TABLE_BASES);
        assertEquals(bases.get(0).getStock(), 5);
    }

    @Test
    public void deleteBase() {
        InventoryModel base;
        ArrayList<InventoryModel> bases = databaseUtility.getAllInventory(DatabaseUtility.TABLE_BASES);
        base = bases.get(0);
        controlPanel.deleteBase(base);

        bases = databaseUtility.getAllInventory(DatabaseUtility.TABLE_BASES);
        assertEquals(bases.size(), 0);
    }

    @Test
    public void addNewTopping() {
        InventoryModel topping = new InventoryModel();
        topping.setName("Ham");
        topping.setCost(0.50);
        topping.setStock(4);
        controlPanel.addTopping(topping);

        ArrayList<InventoryModel> toppings = databaseUtility.getAllInventory(DatabaseUtility.TABLE_TOPPINGS);
        assertTrue(toppings.get(0).getName().equals(topping.getName()));
    }

    @Test
    public void addExistingTopping() {
        InventoryModel topping = new InventoryModel();
        topping.setName("Ham");
        topping.setCost(0.50);
        topping.setStock(4);

        assertFalse(controlPanel.addTopping(topping));
    }

    @Test
    public void updateTopping() {
        InventoryModel topping;
        ArrayList<InventoryModel> toppings = databaseUtility.getAllInventory(DatabaseUtility.TABLE_TOPPINGS);
        topping = toppings.get(0);
        topping.setStock(5);
        controlPanel.updateTopping(topping);

        toppings = databaseUtility.getAllInventory(DatabaseUtility.TABLE_TOPPINGS);
        assertEquals(toppings.get(0).getStock(), 5);
    }

    @Test
    public void deleteTopping() {
        InventoryModel topping;
        ArrayList<InventoryModel> toppings = databaseUtility.getAllInventory(DatabaseUtility.TABLE_TOPPINGS);
        topping = toppings.get(0);
        controlPanel.deleteTopping(topping);

        toppings = databaseUtility.getAllInventory(DatabaseUtility.TABLE_TOPPINGS);
        assertEquals(toppings.size(), 0);
    }

    @Test
    public void editProfile() {
        currentUser.setHash(controlPanel.MD5Hash("admin3", currentUser.getSalt()));
        controlPanel.updateAccount();

        String newHash = currentUser.getHash();
        currentUser = databaseUtility.getUser("admin");

        assertTrue(newHash.equals(currentUser.getHash()));
    }
}
