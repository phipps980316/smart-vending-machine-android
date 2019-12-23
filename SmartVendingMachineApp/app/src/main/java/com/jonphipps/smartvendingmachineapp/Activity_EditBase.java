package com.jonphipps.smartvendingmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Activity_EditBase extends AppCompatActivity { //class to handle the events for the Edit Base Activity
    private AdminControlPanel controlPanel; //Object to hold the control panel
    private InventoryModel base; //Object to hold the base

    private EditText txtBaseCost; //Object to reference the txtBaseCost text field
    private EditText txtBaseStock; //Object to reference the txtBaseStock text field

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the base and control panel objects passed from the last activity
                                                        //Function also populates the activity edit text fields with the product name and cost
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit_base);

        base = (InventoryModel)getIntent().getSerializableExtra("base");
        controlPanel = (AdminControlPanel)getIntent().getSerializableExtra("controlPanel");

        EditText txtBaseName = (EditText) findViewById(R.id.txtBaseName);
        txtBaseName.setText(base.getName());
        txtBaseName.setEnabled(false);

        DecimalFormat currency = new DecimalFormat("0.00");
        txtBaseCost = (EditText) findViewById(R.id.txtBaseCost);
        txtBaseCost.setText(currency.format(base.getCost()));

        txtBaseStock = (EditText) findViewById(R.id.txtBaseStock);
        txtBaseStock.setText(String.valueOf(base.getStock()));
    }

    public void onClickBtnUpdate(View view) { //Event handler for the update button, allows a base to be edited given that all of the fields are filled in and correct
        Toast toast;
        if(!txtBaseCost.getText().toString().isEmpty() && !txtBaseStock.getText().toString().isEmpty()) {
            boolean validInput = false;
            double baseCost = 0;
            int baseStock = 0;
            DecimalFormat currency = new DecimalFormat("0.00");

            try {
                baseCost = Double.valueOf(txtBaseCost.getText().toString());
                baseCost = Double.valueOf(currency.format(baseCost));
                baseStock = Integer.valueOf(txtBaseStock.getText().toString());
                validInput = true;
            } catch (Exception e) {
                toast = Toast.makeText(this, "Invalid Value Entered", Toast.LENGTH_SHORT);
                toast.show();
            }

            if(validInput) {
                base.setCost(baseCost);
                base.setStock(baseStock);
                controlPanel.updateBase(base);
                finish();
            }
        }
        else {
            toast = Toast.makeText(this, "Base Must Have A Cost And Stock Value", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickBtnDelete(View view) { //Event handler for the delete button, allows a base to be deleted by calling the control panel's delete base function
        controlPanel.deleteBase(base);
        finish();
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
