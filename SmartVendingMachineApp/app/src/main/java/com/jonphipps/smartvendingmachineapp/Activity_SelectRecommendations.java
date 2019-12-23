package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity_SelectRecommendations extends AppCompatActivity { //class to handle the events for the Select Recommendations Activity
    private Dispenser dispenser; //Object of type dispenser
    private ArrayList<InventoryModel> inventoryModelArrayList; //List of inventory model objects

    private ListView listView; //Object to reference the list object

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the dispenser object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__select_recommendations);

        dispenser = (Dispenser)getIntent().getSerializableExtra("dispenser");

        listView = (ListView) findViewById(R.id.lstRecommendations);

        populateList();
    }

    private void populateList() { //Function to populate the list of recommendations
        inventoryModelArrayList = new ArrayList<>();
        for (int index = 0; index < dispenser.getSizeOfRecommendations(); index++){
            inventoryModelArrayList.add(dispenser.getRecommendation(index));
        }
        CustomDispenserInventoryArrayAdapter customInventoryArrayAdapter = new CustomDispenserInventoryArrayAdapter(this, R.layout.inventory_item, inventoryModelArrayList);
        listView.setAdapter(customInventoryArrayAdapter);
    }

    public void onClickBtnSelectRecommended(View view) { //Event handler for the select recommended button, calls the dispense's select recommended function if a valid recommendation is selected then function that starts the next activity
        InventoryModel recommended;
        for(int position = 0; position < listView.getAdapter().getCount(); position++)
        {
            recommended = inventoryModelArrayList.get(position);
            if(recommended.getSelected()) dispenser.selectRecommendation(recommended);
        }
        dispenser.selectRecommendation(null);

        Intent intent = new Intent(Activity_SelectRecommendations.this, Activity_ProductSummary.class);
        intent.putExtra("dispenser", dispenser);
        startActivity(intent);

        finish();
    }

    public void onClickBtnBack(View view) {
        finish();
    }
}
