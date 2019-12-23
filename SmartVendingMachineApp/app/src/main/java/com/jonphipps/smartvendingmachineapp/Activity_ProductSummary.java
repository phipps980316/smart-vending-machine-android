package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Activity_ProductSummary extends AppCompatActivity { //class to handle the events for the Product Summary Activity
    private Dispenser dispenser;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the dispenser object passed from the last activity
                                                        //Function also populates the activity text fields
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__product_summary);

        dispenser = (Dispenser)getIntent().getSerializableExtra("dispenser");
        TextView txtProductName = (TextView) findViewById(R.id.txtProductName);
        txtProductName.setText("Item Name: " + dispenser.getItemToDispense().getProductName());
        TextView txtProductCost = (TextView) findViewById(R.id.txtProductCost);
        DecimalFormat currency = new DecimalFormat("0.00");
        txtProductCost.setText("Item Cost: £" + currency.format(dispenser.getItemToDispense().getProductCost()));
    }

    public void onClickBtnConfirm(View view){ //Event handler for the confirm button, calls the dispenser confirmation function and starts the next activity depending on the state of the dispenser
        dispenser.confirmation(true);

        if (dispenser.getStateID() == State.INSERT_CREDIT) {
            Intent intent = new Intent(Activity_ProductSummary.this, Activity_InsertCredit.class);
            intent.putExtra("dispenser", dispenser);
            startActivity(intent);
        }
        else if(dispenser.getStateID() == State.DISPENSE_ITEM) {
            Intent intent = new Intent(Activity_ProductSummary.this, Activity_DispenseItem.class);
            intent.putExtra("dispenser", dispenser);
            startActivity(intent);
        }
        finish();
    }

    public void onClickBtnRestart(View view){ //Event handler for the restart button, calls the dispenser confirmation function and starts the next activity which resets the dispenser
        dispenser.confirmation(false);
        Intent intent = new Intent(Activity_ProductSummary.this, Activity_SelectBase.class);
        intent.putExtra("dispenser", dispenser);
        startActivity(intent);
        finish();
    }
}
