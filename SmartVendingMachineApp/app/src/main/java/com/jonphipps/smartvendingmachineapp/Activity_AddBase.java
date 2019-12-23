package com.jonphipps.smartvendingmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Activity_AddBase extends AppCompatActivity { //class to handle the events for the Add Base Activity
    private AdminControlPanel controlPanel; //Object to hold the control panel

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the control panel object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add_base);

        controlPanel = (AdminControlPanel)getIntent().getSerializableExtra("controlPanel");
    }

    public void onClickBtnAdd(View view) { //Event handler for the add button, allows a new base to be added given that all of the fields are filled in and correct
        EditText txtBaseName = (EditText)findViewById(R.id.txtBaseName);
        EditText txtBaseCost = (EditText)findViewById(R.id.txtBaseCost);
        EditText txtBaseStock = (EditText)findViewById(R.id.txtBaseStock);
        Toast toast;

        if(!txtBaseName.getText().toString().isEmpty() && !txtBaseCost.getText().toString().isEmpty() && !txtBaseStock.getText().toString().isEmpty()){
            boolean validInput = false;
            String baseName = "";
            double baseCost = 0;
            int baseStock = 0;
            DecimalFormat currency = new DecimalFormat("0.00");

            try {
                baseName = txtBaseName.getText().toString();
                baseCost = Double.valueOf(txtBaseCost.getText().toString());
                baseCost = Double.valueOf(currency.format(baseCost));
                baseStock = Integer.valueOf(txtBaseStock.getText().toString());
                validInput = true;
            } catch (Exception e) {
                toast = Toast.makeText(this, "Invalid Value Entered", Toast.LENGTH_SHORT);
                toast.show();
            }

            if(validInput) {
                InventoryModel base = new InventoryModel();
                base.setName(baseName);
                base.setCost(baseCost);
                base.setStock(baseStock);
                if (controlPanel.addBase(base)) {
                    finish();
                } else {
                    toast = Toast.makeText(this, "Base Name Already Exists", Toast.LENGTH_SHORT);
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
