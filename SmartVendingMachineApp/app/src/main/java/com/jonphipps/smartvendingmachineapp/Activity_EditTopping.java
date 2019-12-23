package com.jonphipps.smartvendingmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Activity_EditTopping extends AppCompatActivity { //class to handle the events for the Edit Topping Activity
    private AdminControlPanel controlPanel; //Object to hold the control panel
    private InventoryModel topping; //Object to hold the topping

    private EditText txtToppingCost; //Object to reference the txtToppingCost text field
    private EditText txtToppingStock; //Object to reference the txtToppingStock text field

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the topping and control panel objects passed from the last activity
                                                        //Function also populates the activity edit text fields with the product name and cost
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit_topping);

        topping = (InventoryModel)getIntent().getSerializableExtra("topping");
        controlPanel = (AdminControlPanel)getIntent().getSerializableExtra("controlPanel");

        EditText txtToppingName = (EditText) findViewById(R.id.txtToppingName);
        txtToppingName.setText(topping.getName());
        txtToppingName.setEnabled(false);

        DecimalFormat currency = new DecimalFormat("0.00");
        txtToppingCost = (EditText) findViewById(R.id.txtToppingCost);
        txtToppingCost.setText(currency.format(topping.getCost()));

        txtToppingStock = (EditText) findViewById(R.id.txtToppingStock);
        txtToppingStock.setText(String.valueOf(topping.getStock()));
    }

    public void onClickBtnUpdate(View view) { //Event handler for the update button, allows a topping to be edited given that all of the fields are filled in and correct
        Toast toast;
        if(!txtToppingCost.getText().toString().isEmpty() && !txtToppingStock.getText().toString().isEmpty()) {
            boolean validInput = false;
            double toppingCost = 0;
            int toppingStock = 0;
            DecimalFormat currency = new DecimalFormat("0.00");

            try {
                toppingCost = Double.valueOf(txtToppingCost.getText().toString());
                toppingCost = Double.valueOf(currency.format(toppingCost));
                toppingStock = Integer.valueOf(txtToppingStock.getText().toString());
                validInput = true;
            } catch (Exception e) {
                toast = Toast.makeText(this, "Invalid Value Entered", Toast.LENGTH_SHORT);
                toast.show();
            }

            if(validInput) {
                topping.setCost(toppingCost);
                topping.setStock(toppingStock);
                controlPanel.updateTopping(topping);
                finish();
            }
        }
        else {
            toast = Toast.makeText(this, "Topping Must Have A Cost And Stock Value", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickBtnDelete(View view) { //Event handler for the delete button, allows a topping to be deleted by calling the control panel's delete topping function
        controlPanel.deleteTopping(topping);
        finish();
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
