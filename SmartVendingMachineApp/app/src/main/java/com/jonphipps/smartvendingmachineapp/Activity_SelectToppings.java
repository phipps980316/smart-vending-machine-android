package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_SelectToppings extends AppCompatActivity { //class to handle the events for the Select Topping Activity
    private Dispenser dispenser; //Object of type dispenser
    private ArrayList<InventoryModel> inventoryModelArrayList; //List of inventory model objects

    private ListView listView; //Object to reference the list object

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the dispenser object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__select_toppings);

        dispenser = (Dispenser)getIntent().getSerializableExtra("dispenser");

        listView = (ListView) findViewById(R.id.lstToppings);

        populateList();

        if(inventoryModelArrayList.size() == 0){
            Toast toast = Toast.makeText(this, "No Topping Products Are Present In The Machine, Please Use Admin Control Panel To Add Topping Products", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void populateList() { //Function to populate the list of bases with values fetched from the database
        inventoryModelArrayList = dispenser.getToppingsInventoryList();
        CustomDispenserInventoryArrayAdapter customInventoryArrayAdapter = new CustomDispenserInventoryArrayAdapter(this, R.layout.inventory_item, inventoryModelArrayList);
        listView.setAdapter(customInventoryArrayAdapter);
    }

    public void onClickBtnSelectToppings(View view) { //Event handler for the select topping button, calls the dispense's select topping function if a valid topping is selected then function starts the next activity
        InventoryModel topping;
        for(int position = 0; position < listView.getAdapter().getCount(); position++)
        {
            topping = inventoryModelArrayList.get(position);
            if(topping.getSelected()) dispenser.selectTopping(topping);
        }
        dispenser.selectTopping(null);
        dispenser.findRecommendations();

        if (dispenser.getStateID() == State.SELECT_RECOMMENDATIONS) {
            Intent intent = new Intent(Activity_SelectToppings.this, Activity_SelectRecommendations.class);
            intent.putExtra("dispenser", dispenser);
            startActivity(intent);
        }
        else if(dispenser.getStateID() == State.PRODUCT_SUMMARY) {
            Intent intent = new Intent(Activity_SelectToppings.this, Activity_ProductSummary.class);
            intent.putExtra("dispenser", dispenser);
            startActivity(intent);
        }
        finish();
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
