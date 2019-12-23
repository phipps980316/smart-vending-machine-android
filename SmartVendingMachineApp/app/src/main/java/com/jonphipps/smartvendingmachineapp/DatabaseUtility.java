package com.jonphipps.smartvendingmachineapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.io.Serializable;
import java.util.ArrayList;

public class DatabaseUtility implements Serializable { //Class to handle the database operations
    private final String DATABASE_NAME = "/data/data/com.jonphipps.smartvendingmachineapp/vending_machine.db"; //String to hold the address of the database

    //Strings to hold the names of the database tables
    public static final String TABLE_BASES = "Bases";
    public static final String TABLE_TOPPINGS = "Toppings";
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_TOPPINGORDERS = "ToppingOrders";
    public static final String TABLE_BASEORDERS = "BaseOrders";
    public static final String TABLE_ORDERHISTORY = "OrderHistory";

    //object of database utility that is used to maintain a single instance of this class
    private static DatabaseUtility singleInstance = null;

    private DatabaseUtility() { //constructor that creates and opens the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        SQLiteStatement sqLiteStatement;

        final String CREATE_DATABASE_TABLE_TOPPINGS = "CREATE TABLE IF NOT EXISTS " + TABLE_TOPPINGS
                + " (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "Name TEXT NOT NULL,"
                + "Cost DOUBLE NOT NULL,"
                + "Stock INTEGER NOT NULL);";
        sqLiteStatement = database.compileStatement(CREATE_DATABASE_TABLE_TOPPINGS);
        sqLiteStatement.execute();

        final String CREATE_DATABASE_TABLE_BASES = "CREATE TABLE IF NOT EXISTS " + TABLE_BASES
                + " (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "Name TEXT NOT NULL,"
                + "Cost DOUBLE NOT NULL,"
                + "Stock INTEGER NOT NULL);";
        sqLiteStatement = database.compileStatement(CREATE_DATABASE_TABLE_BASES);
        sqLiteStatement.execute();

        final String CREATE_DATABASE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS
                + " (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "Name TEXT NOT NULL,"
                + "Hash TEXT NOT NULL,"
                + "Salt TEXT NOT NULL,"
                + "Role TEXT NOT NULL,"
                + "Credit DOUBLE NOT NULL);";
        sqLiteStatement = database.compileStatement(CREATE_DATABASE_TABLE_USERS);
        sqLiteStatement.execute();

        final String CREATE_DATABASE_TABLE_TOPPINGORDERS = "CREATE TABLE IF NOT EXISTS " + TABLE_TOPPINGORDERS
                + " (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "UserID INTEGER NOT NULL,"
                + "ItemID INTEGER NOT NULL,"
                + "FOREIGN KEY(UserID) REFERENCES Users(ID),"
                + "FOREIGN KEY(ItemID) REFERENCES Toppings(ID));";
        sqLiteStatement = database.compileStatement(CREATE_DATABASE_TABLE_TOPPINGORDERS);
        sqLiteStatement.execute();

        final String CREATE_DATABASE_TABLE_BASEORDERS = "CREATE TABLE IF NOT EXISTS " + TABLE_BASEORDERS
                + " (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "UserID INTEGER NOT NULL,"
                + "ItemID INTEGER NOT NULL,"
                + "FOREIGN KEY(UserID) REFERENCES Users(ID),"
                + "FOREIGN KEY(ItemID) REFERENCES Bases(ID));";
        sqLiteStatement = database.compileStatement(CREATE_DATABASE_TABLE_BASEORDERS);
        sqLiteStatement.execute();

        final String CREATE_DATABASE_TABLE_ORDERHISTORY = "CREATE TABLE IF NOT EXISTS OrderHistory"
                + "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "UserID INTEGER NOT NULL,"
                + "OrderDate TEXT NOT NULL,"
                + "ProductName TEXT NOT NULL,"
                + "ProductCost DOUBLE NOT NULL,"
                + "FOREIGN KEY(UserID) REFERENCES Users(ID));";
        sqLiteStatement = database.compileStatement(CREATE_DATABASE_TABLE_ORDERHISTORY);
        sqLiteStatement.execute();
        database.close();
    }

    public static DatabaseUtility getInstance() { //function to create a single instance of this class if one does not exist else returns the existing single instance
        if(singleInstance == null) singleInstance = new DatabaseUtility();
        return singleInstance;
    }

    public UserModel getUser(String name) { //function searches the database for a user with the given user name and returns the user model object if a user is found
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        UserModel user = null;
        String[] columns = {"ID","Name","Hash","Salt","Credit","Role"};
        String selection = "Name = ?";
        String[] selectionParameters = {name};
        Cursor cursor = database.query(TABLE_USERS, columns, selection, selectionParameters, null, null, null);

        while (cursor.moveToNext()) {
            user = new UserModel();
            user.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            user.setName(cursor.getString(cursor.getColumnIndex("Name")));
            user.setHash(cursor.getString(cursor.getColumnIndex("Hash")));
            user.setSalt(cursor.getString(cursor.getColumnIndex("Salt")));
            user.setCredit(cursor.getDouble(cursor.getColumnIndex("Credit")));
            user.setRole(cursor.getString(cursor.getColumnIndex("Role")));
        }
        cursor.close();
        database.close();
        return user;
    }

    public ArrayList<UserModel> getAllUsers() { //function searches the database for all users and returns a list of user model objects
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        ArrayList<UserModel> userModelArrayList = new ArrayList<>();
        String[] columns = {"ID","Name","Hash","Salt","Credit","Role"};
        Cursor cursor = database.query(TABLE_USERS, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            UserModel userModel = new UserModel();
            userModel.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            userModel.setName(cursor.getString(cursor.getColumnIndex("Name")));
            userModel.setHash(cursor.getString(cursor.getColumnIndex("Hash")));
            userModel.setSalt(cursor.getString(cursor.getColumnIndex("Salt")));
            userModel.setCredit(cursor.getDouble(cursor.getColumnIndex("Credit")));
            userModel.setRole(cursor.getString(cursor.getColumnIndex("Role")));
            userModelArrayList.add(userModel);
        }
        cursor.close();
        database.close();
        return userModelArrayList;
    }

    public int getUserCount() { //function gets the user count from the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sqlQuery = "SELECT count(*) FROM Users";
        SQLiteStatement sqLiteStatement = database.compileStatement(sqlQuery);
        int result = (int)sqLiteStatement.simpleQueryForLong();
        database.close();
        return result;
    }

    public boolean checkNameTaken(String table, String name) { //function searches a given table for a given name and returns true if an entry with the given name is found
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        boolean taken = false;
        String[] columns = {"Name"};
        String selection = "Name = ?";
        String[] selectionParameters = {name};
        Cursor cursor = database.query(table, columns, selection, selectionParameters, null, null, null);

        if (cursor.getCount() > 0) taken = true;
        cursor.close();
        database.close();
        return taken;
    }

    public void newUser(UserModel user) { //function adds a new user to the user table of the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sql = "INSERT INTO Users (Name, Hash, Salt, Role, Credit) VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindString(1, user.getName());
        sqLiteStatement.bindString(2, user.getHash());
        sqLiteStatement.bindString(3, user.getSalt());
        sqLiteStatement.bindString(4, user.getRole());
        sqLiteStatement.bindDouble(5, user.getCredit());
        sqLiteStatement.executeInsert();
        database.close();
    }

    public void updateUser(UserModel user){ //function updates an existing user in the user table of the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sql = "UPDATE Users SET Name=?, Hash=?, Salt=?, Role=?, Credit=? WHERE ID = ?";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindString(1, user.getName());
        sqLiteStatement.bindString(2, user.getHash());
        sqLiteStatement.bindString(3, user.getSalt());
        sqLiteStatement.bindString(4, user.getRole());
        sqLiteStatement.bindDouble(5, user.getCredit());
        sqLiteStatement.bindLong(6, user.getId());
        sqLiteStatement.executeUpdateDelete();
        database.close();
    }

    public void deleteUser(UserModel user) { //function to delete a user from the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sql = "DELETE FROM Users WHERE ID = ?";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindLong(1,user.getId());
        sqLiteStatement.executeUpdateDelete();
        database.close();
    }

    public ArrayList<InventoryModel> getInventoryList(String table) { //function to fetch all the in stock items from a given table
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        ArrayList<InventoryModel> inventoryModelArrayList = new ArrayList<>();
        String[] columns = {"ID","Name","Cost","Stock"};
        String selection = "Stock > ?";
        String[] selectionParameters = {"0"};
        Cursor cursor = database.query(table, columns, selection, selectionParameters, null, null, null);

        while (cursor.moveToNext()) {
            InventoryModel inventoryModel = new InventoryModel();
            inventoryModel.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            inventoryModel.setName(cursor.getString(cursor.getColumnIndex("Name")));
            inventoryModel.setCost(cursor.getDouble(cursor.getColumnIndex("Cost")));
            inventoryModel.setStock(cursor.getInt(cursor.getColumnIndex("Stock")));
            inventoryModelArrayList.add(inventoryModel);
        }
        cursor.close();
        database.close();
        return inventoryModelArrayList;
    }

    public ArrayList<InventoryModel> getAllInventory(String table) { //function to fetch all the item from a given table
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        ArrayList<InventoryModel> inventoryModelArrayList = new ArrayList<>();
        String[] columns = {"ID","Name","Cost","Stock"};
        Cursor cursor = database.query(table, columns, null, null ,null, null, null, null);

        while (cursor.moveToNext()) {
            InventoryModel inventoryModel = new InventoryModel();
            inventoryModel.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            inventoryModel.setName(cursor.getString(cursor.getColumnIndex("Name")));
            inventoryModel.setCost(cursor.getDouble(cursor.getColumnIndex("Cost")));
            inventoryModel.setStock(cursor.getInt(cursor.getColumnIndex("Stock")));
            inventoryModelArrayList.add(inventoryModel);
        }
        cursor.close();
        database.close();
        return inventoryModelArrayList;
    }

    public void addInventory(String table, InventoryModel inventory) { //function adds an inventory item to a given table
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sql = "INSERT INTO " + table + " (Name, Cost, Stock) VALUES (?, ?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindString(1, inventory.getName());
        sqLiteStatement.bindDouble(2, inventory.getCost());
        sqLiteStatement.bindLong(3, inventory.getStock());
        sqLiteStatement.executeInsert();
        database.close();
    }

    public void updateInventory(String table, InventoryModel inventory){ //function to update an existing inventory item in a given table
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sql = "UPDATE " + table + " SET Name=?, Cost=?, Stock=? WHERE ID = ?";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindString(1, inventory.getName());
        sqLiteStatement.bindDouble(2, inventory.getCost());
        sqLiteStatement.bindLong(3, inventory.getStock());
        sqLiteStatement.bindLong(4, inventory.getId());
        sqLiteStatement.executeUpdateDelete();
        database.close();
    }

    public void deleteInventory(String inventoryTable, String recordTable, InventoryModel inventory) { //function to delete an inventory item in a given table
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sql = "DELETE FROM " + inventoryTable + " WHERE ID = ?";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindLong(1, inventory.getId());
        sqLiteStatement.executeUpdateDelete();

        sqLiteStatement.clearBindings();

        sql = "DELETE FROM " + recordTable + " WHERE ItemID = ?";
        sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindLong(1, inventory.getId());
        sqLiteStatement.executeUpdateDelete();
        database.close();
    }

    public void recordItemSale(String table, UserModel user, InventoryModel inventory){ //function to record the sale of an item
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sql = "INSERT INTO " + table + " (UserID, ItemID) VALUES (?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindLong(1, user.getId());
        sqLiteStatement.bindLong(2, inventory.getId());
        sqLiteStatement.executeInsert();
        database.close();
    }

    public ArrayList<OrderRecordModel> getAllToppingSales() { //function to get all of the topping sales data from the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        ArrayList<OrderRecordModel> orderRecordModelArrayList = new ArrayList<>();
        String[] columns = {"ID","UserID","ItemID"};
        Cursor cursor = database.query(TABLE_TOPPINGORDERS, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            OrderRecordModel orderRecordModel = new OrderRecordModel();
            orderRecordModel.setUserID(cursor.getInt(cursor.getColumnIndex("UserID")));
            orderRecordModel.setItemID(cursor.getInt(cursor.getColumnIndex("ItemID")));
            orderRecordModelArrayList.add(orderRecordModel);
        }
        cursor.close();
        database.close();
        return orderRecordModelArrayList;
    }

    public void recordOrderHistory(UserModel user, Product product, String date) { //function to record order history
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sql = "INSERT INTO OrderHistory (UserID, OrderDate, ProductName, ProductCost) VALUES (?, ?, ?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindLong(1, user.getId());
        sqLiteStatement.bindString(2, date);
        sqLiteStatement.bindString(3, product.getProductName());
        sqLiteStatement.bindDouble(4, product.getProductCost());
        sqLiteStatement.executeInsert();
        database.close();
    }

    public ArrayList<OrderRecordModel> getUserOrders(UserModel user){ //function to get all the order history of a given user
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        ArrayList<OrderRecordModel> orderRecordModelArrayList = new ArrayList<>();
        String[] columns = {"ID","UserID","OrderDate","ProductName","ProductCost"};
        String selection = "UserID = ?";
        String[] selectionParameters = {String.valueOf(user.getId())};
        Cursor cursor = database.query(TABLE_ORDERHISTORY, columns, selection, selectionParameters, null, null, null);

        while (cursor.moveToNext()) {
            OrderRecordModel orderRecordModel = new OrderRecordModel();
            orderRecordModel.setItemID(cursor.getInt(cursor.getColumnIndex("ID")));
            orderRecordModel.setUserID(cursor.getInt(cursor.getColumnIndex("UserID")));
            orderRecordModel.setOrderDate(cursor.getString(cursor.getColumnIndex("OrderDate")));
            orderRecordModel.setProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
            orderRecordModel.setProductCost(cursor.getDouble(cursor.getColumnIndex("ProductCost")));
            orderRecordModelArrayList.add(orderRecordModel);
        }
        cursor.close();
        database.close();
        return orderRecordModelArrayList;
    }

    public int getOrderCount() { //function to get the order count from the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        String sqlQuery = "SELECT count(*) FROM OrderHistory";
        SQLiteStatement sqLiteStatement = database.compileStatement(sqlQuery);
        int result = (int)sqLiteStatement.simpleQueryForLong();
        database.close();
        return result;
    }

    public double getTotalOrderAmount() { //function to get the total order amount from the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        double result = 0;
        String[] columns = {"sum(ProductCost)"};
        Cursor cursor = database.query(TABLE_ORDERHISTORY, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result = cursor.getDouble(cursor.getColumnIndex("sum(ProductCost)"));
        }
        cursor.close();
        database.close();
        return result;
    }

    public ArrayList<InventoryModel> getOutOfStockItems(String table) { //function to get all of the out of stock items from a given table
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        ArrayList<InventoryModel> inventoryModelArrayList = new ArrayList<>();
        String[] columns = {"ID","Name","Cost","Stock"};
        String selection = "Stock = ?";
        String[] selectionParameters = {"0"};
        Cursor cursor = database.query(table, columns, selection, selectionParameters, null, null, null);

        while (cursor.moveToNext()) {
            InventoryModel inventoryModel = new InventoryModel();
            inventoryModel.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            inventoryModel.setName(cursor.getString(cursor.getColumnIndex("Name")));
            inventoryModel.setCost(cursor.getDouble(cursor.getColumnIndex("Cost")));
            inventoryModel.setStock(cursor.getInt(cursor.getColumnIndex("Stock")));
            inventoryModelArrayList.add(inventoryModel);
        }
        cursor.close();
        database.close();
        return inventoryModelArrayList;
    }

    public ArrayList<InventoryModel> getBasesSold(){ //function to get all the base sales data from the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        ArrayList<InventoryModel> inventoryModelArrayList = new ArrayList<>();
        String table = "Bases LEFT JOIN BaseOrders ON Bases.ID = BaseOrders.ItemID";
        String[] columns = {"Bases.ID", "Bases.Name", "count(BaseOrders.ItemID) as Quantity"};
        String groupBy = "BaseOrders.ItemID";
        String orderBy = "count(BaseOrders.ItemID) DESC";
        Cursor cursor = database.query(table, columns, null, null, groupBy, null, orderBy);

        while (cursor.moveToNext()) {
            InventoryModel inventoryModel = new InventoryModel();
            inventoryModel.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            inventoryModel.setName(cursor.getString(cursor.getColumnIndex("Name")));
            inventoryModel.setNumberSold(cursor.getInt(cursor.getColumnIndex("Quantity")));
            inventoryModelArrayList.add(inventoryModel);
        }
        cursor.close();
        database.close();
        return inventoryModelArrayList;
    }

    public ArrayList<InventoryModel> getToppingsSold(){ //function to get all the topping sales data from the database
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        ArrayList<InventoryModel> inventoryModelArrayList = new ArrayList<>();
        String table = "Toppings LEFT JOIN ToppingOrders ON Toppings.ID = ToppingOrders.ItemID";
        String[] columns = {"Toppings.ID", "Toppings.Name", "count(ToppingOrders.ItemID) as Quantity"};
        String groupBy = "ToppingOrders.ItemID";
        String orderBy = "count(ToppingOrders.ItemID) DESC";
        Cursor cursor = database.query(table, columns, null, null, groupBy, null, orderBy);

        while (cursor.moveToNext()) {
            InventoryModel inventoryModel = new InventoryModel();
            inventoryModel.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            inventoryModel.setName(cursor.getString(cursor.getColumnIndex("Name")));
            inventoryModel.setNumberSold(cursor.getInt(cursor.getColumnIndex("Quantity")));
            inventoryModelArrayList.add(inventoryModel);
        }
        cursor.close();
        database.close();
        return inventoryModelArrayList;
    }

}

