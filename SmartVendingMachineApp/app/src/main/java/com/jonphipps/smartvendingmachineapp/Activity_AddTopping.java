package com.jonphipps.smartvendingmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Activity_AddTopping extends AppCompatActivity { //class to handle the events for the Add Topping Activity
    private AdminControlPanel controlPanel; //Object to hold the control panel

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the control panel object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add_topping);

        controlPanel = (AdminControlPanel)getIntent().getSerializableExtra("controlPanel");
    }

    public void onClickBtnAdd(View view) { //Event handler for the add button, allows a new topping to be added given that all of the fields are filled in and correct
        EditText txtToppingName = (EditText)findViewById(R.id.txtToppingName);
        EditText txtToppingCost = (EditText)findViewById(R.id.txtToppingCost);
        EditText txtToppingStock = (EditText)findViewById(R.id.txtToppingStock);
        Toast toast;

        if(!txtToppingName.getText().toString().isEmpty() && !txtToppingCost.getText().toString().isEmpty() && !txtToppingStock.getText().toString().isEmpty()){
            boolean validInput = false;
            String toppingName = "";
            double toppingCost = 0;
            int toppingStock = 0;
            DecimalFormat currency = new DecimalFormat("0.00");

            try {
                toppingName = txtToppingName.getText().toString();
                toppingCost = Double.valueOf(txtToppingCost.getText().toString());
                toppingCost = Double.valueOf(currency.format(toppingCost));
                toppingStock = Integer.valueOf(txtToppingStock.getText().toString());
                validInput = true;
            } catch (Exception e) {
                toast = Toast.makeText(this, "Invalid Value Entered", Toast.LENGTH_SHORT);
                toast.show();
            }

            if(validInput) {
                InventoryModel topping = new InventoryModel();
                topping.setName(toppingName);
                topping.setCost(toppingCost);
                topping.setStock(toppingStock);
                if(controlPanel.addTopping(topping)) {
                    finish();
                }
                else {
                    toast = Toast.makeText(this, "Topping Name Already Exists", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        else {
            toast = Toast.makeText(this, "All Fields Required", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
