package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Activity_InsertCredit extends AppCompatActivity { //class to handle the events for the Insert Credit Activity
    private Dispenser dispenser; //Object to hold the dispenser

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the dispenser object passed from the last activity
                                                        //Function also populates the activity edit text fields
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__insert_credit);

        dispenser = (Dispenser)getIntent().getSerializableExtra("dispenser");
        DecimalFormat currency = new DecimalFormat("0.00");

        TextView lblCurrentCredit = (TextView) findViewById(R.id.lblCurrentCredit);
        lblCurrentCredit.setText("Current Credit: £" + currency.format(dispenser.getUser().getCredit()));

        TextView lblCreditNeeded = (TextView) findViewById(R.id.lblCreditNeeded);
        lblCreditNeeded.setText("Credit Needed: £" + currency.format(dispenser.getItemToDispense().getProductCost() - dispenser.getUser().getCredit()));
    }

    public void onClickBtnEnterCredit(View view) { //Event handler for the enter credit button, allows the user to add credit to the machine given the input is valid
        EditText txtCredit = (EditText) findViewById(R.id.txtCredit);
        Toast toast;
        if(!txtCredit.getText().toString().isEmpty()){
            boolean validInput = false;
            double insertedCredit = 0;
            DecimalFormat currency = new DecimalFormat("0.00");

            try {
                insertedCredit = Double.parseDouble(txtCredit.getText().toString());
                insertedCredit = Double.parseDouble(currency.format(insertedCredit));
                validInput = true;
            } catch (NumberFormatException e) {
                toast = Toast.makeText(this, "Invalid Amount Entered", Toast.LENGTH_SHORT);
                toast.show();
            }

            if(validInput) {
                if (dispenser.insertCredit(insertedCredit)) {
                    Intent intent = new Intent(Activity_InsertCredit.this, Activity_DispenseItem.class);
                    intent.putExtra("dispenser", dispenser);
                    startActivity(intent);
                    finish();
                }
                else {
                    toast = Toast.makeText(this, "Please Enter The Credit Needed At Minimum", Toast.LENGTH_SHORT);
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
