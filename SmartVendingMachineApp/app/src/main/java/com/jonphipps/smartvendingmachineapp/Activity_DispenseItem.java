package com.jonphipps.smartvendingmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Activity_DispenseItem extends AppCompatActivity { //class to handle the events for the Dispense Item Activity
    private Dispenser dispenser; //Object to hold the dispenser

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the dispenser object passed from the last activity
                                                        //Function also populates the activity labels with the product name and cost
                                                        //Finally the functions calls the dispenser's dispense function
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dispense_item);

        dispenser = (Dispenser)getIntent().getSerializableExtra("dispenser");
        DecimalFormat currency = new DecimalFormat("0.00");

        TextView lblItemName = (TextView) findViewById(R.id.lblItemName);
        lblItemName.setText("Item Name: " + dispenser.getItemToDispense().getProductName());

        TextView lblItemCost = (TextView) findViewById(R.id.lblItemCost);
        lblItemCost.setText("Item Cost: £" + currency.format(dispenser.getItemToDispense().getProductCost()));

        dispenser.dispense();
    }

    public void onClickBtnFinish(View view){ //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}