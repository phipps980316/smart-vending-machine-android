package com.jonphipps.smartvendingmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Activity_ViewSalesData extends AppCompatActivity { //class to handle the events for the View Sales Data Activity
    private AdminControlPanel controlPanel; //Object of type admin control panel

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the control panel object passed from the last activity
                                                        //Function also populates the activity text fields
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_sales_data);

        controlPanel = (AdminControlPanel)getIntent().getSerializableExtra("controlPanel");

        TextView totalNumberOfUsers = (TextView) findViewById(R.id.lblTotalUsers);
        totalNumberOfUsers.setText("Total Number Of Users : " + String.valueOf(controlPanel.getTotalNumberOfUsers()));

        TextView totalNumberOfOrders = (TextView) findViewById(R.id.lblTotalOrders);
        totalNumberOfOrders.setText("Total Number Of Orders : " + String.valueOf(controlPanel.getTotalNumberOfOrders()));

        DecimalFormat currency = new DecimalFormat("0.00");
        TextView totalAmountOfSales = (TextView) findViewById(R.id.lblTotalSalesAmount);
        totalAmountOfSales.setText("Total Sales Amount : £" + currency.format(controlPanel.getTotalOrderAmount()));

        populateBaseSalesTable();
        populateToppingSalesTable();
        populateOutOfStockBasesTable();
        populateOutOfStockToppingsTable();


    }

    private void populateBaseSalesTable() { //Function to populate the list of base sales data with values fetched from the database
        TableLayout numberOfBasesSoldTable = (TableLayout) findViewById(R.id.tbNumberOfBaseItemsSold);
        ArrayList<InventoryModel> basesSold = controlPanel.getBasesSold();
        int numberOfItems = basesSold.size();

        for(int index = 0; index < numberOfItems; index++){
            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.sales_data_table_row_with_3_columns, null, false);

            TextView itemID = (TextView) tableRow.findViewById(R.id.rwItemID);
            TextView itemName = (TextView) tableRow.findViewById(R.id.rwItemName);
            TextView itemQuantity = (TextView) tableRow.findViewById(R.id.rwItemQty);

            InventoryModel currentItem = basesSold.get(index);

            itemID.setText(String.valueOf(currentItem.getId()));
            itemName.setText(currentItem.getName());
            itemQuantity.setText(String.valueOf(currentItem.getNumberSold()));

            numberOfBasesSoldTable.addView(tableRow);
        }
    }

    private void populateToppingSalesTable() { //Function to populate the list of topping sales data with values fetched from the database
        TableLayout numberOfToppingsSoldTable = (TableLayout) findViewById(R.id.tbNumberOfToppingItemsSold);
        ArrayList<InventoryModel> toppingsSold = controlPanel.getToppingsSold();
        int numberOfItems = toppingsSold.size();

        for(int index = 0; index < numberOfItems; index++){
            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.sales_data_table_row_with_3_columns, null, false);

            TextView itemID = (TextView) tableRow.findViewById(R.id.rwItemID);
            TextView itemName = (TextView) tableRow.findViewById(R.id.rwItemName);
            TextView itemQuantity = (TextView) tableRow.findViewById(R.id.rwItemQty);

            InventoryModel currentItem = toppingsSold.get(index);

            itemID.setText(String.valueOf(currentItem.getId()));
            itemName.setText(currentItem.getName());
            itemQuantity.setText(String.valueOf(currentItem.getNumberSold()));

            numberOfToppingsSoldTable.addView(tableRow);
        }
    }

    private void populateOutOfStockBasesTable() { //Function to populate the list of out of stock bases with values fetched from the database
        TableLayout outOfStockBasesTable = (TableLayout) findViewById(R.id.tbOutOfStockBaseItems);
        ArrayList<InventoryModel> outOfStockBases = controlPanel.getOutOfStockBases();
        int numberOfItems = outOfStockBases.size();
        for(int index = 0; index < numberOfItems; index++){
            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.sales_data_table_row_with_2_columns, null, false);

            TextView itemID = (TextView) tableRow.findViewById(R.id.rwItemID);
            TextView itemName = (TextView) tableRow.findViewById(R.id.rwItemName);

            InventoryModel currentItem = outOfStockBases.get(index);

            itemID.setText(String.valueOf(currentItem.getId()));
            itemName.setText(currentItem.getName());

            outOfStockBasesTable.addView(tableRow);
        }
    }

    private void populateOutOfStockToppingsTable() { //Function to populate the list of out of stock toppings with values fetched from the database
        TableLayout outOfStockToppingsTable = (TableLayout) findViewById(R.id.tbOutOfStockToppingItems);
        ArrayList<InventoryModel> outOfStockToppings = controlPanel.getOutOfStockToppings();
        int numberOfItems = outOfStockToppings.size();
        for(int index = 0; index < numberOfItems; index++) {
            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.sales_data_table_row_with_2_columns, null, false);

            TextView itemID = (TextView) tableRow.findViewById(R.id.rwItemID);
            TextView itemName = (TextView) tableRow.findViewById(R.id.rwItemName);

            InventoryModel currentItem = outOfStockToppings.get(index);

            itemID.setText(String.valueOf(currentItem.getId()));
            itemName.setText(currentItem.getName());

            outOfStockToppingsTable.addView(tableRow);
        }
    }

    public void onClickBtnBack(View view) {  //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
