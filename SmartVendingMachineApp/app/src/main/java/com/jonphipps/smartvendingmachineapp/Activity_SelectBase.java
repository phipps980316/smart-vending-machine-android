package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_SelectBase extends AppCompatActivity { //class to handle the events for the Select Base Activity
    private Dispenser dispenser; //Object of type dispenser
    private ArrayList<InventoryModel> inventoryModelArrayList; //List of inventory model objects

    private ListView listView; //Object to reference the list object

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the dispenser object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__select_base);

        dispenser = (Dispenser)getIntent().getSerializableExtra("dispenser");

        listView = (ListView) findViewById(R.id.lstBases);

        populateList();

        if(inventoryModelArrayList.size() == 0){
            Toast toast = Toast.makeText(this, "No Base Products Are In Stock, Please Use Admin Control Panel To Add More Stock", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void populateList() { //Function to populate the list of bases with values fetched from the database
        inventoryModelArrayList = dispenser.getBasesInventoryList();
        CustomDispenserInventoryArrayAdapter customDispenserInventoryArrayAdapter = new CustomDispenserInventoryArrayAdapter(this, R.layout.inventory_item, inventoryModelArrayList);
        listView.setAdapter(customDispenserInventoryArrayAdapter);
    }

    public void onClickBtnSelectBase(View view) { //Event handler for the select base button, calls the dispense's select base function if a valid base is selected then function starts the next activity
        ArrayList<InventoryModel> selectedBases = new ArrayList<>();
        InventoryModel base;
        Toast toast;

        for(int position = 0; position < inventoryModelArrayList.size(); position++)
        {
            base = inventoryModelArrayList.get(position);
            if(base.getSelected()) selectedBases.add(base);
        }
        if (selectedBases.isEmpty()) {
            toast = Toast.makeText(this, "No Base Has Been Selected", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (selectedBases.size() == 1) {
            dispenser.selectBase(selectedBases.get(0));
            Intent intent = new Intent(Activity_SelectBase.this, Activity_SelectToppings.class);
            intent.putExtra("dispenser", dispenser);
            startActivity(intent);
            finish();
        }
        else {
            toast = Toast.makeText(this, "Only One Base Can Be Selected", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
