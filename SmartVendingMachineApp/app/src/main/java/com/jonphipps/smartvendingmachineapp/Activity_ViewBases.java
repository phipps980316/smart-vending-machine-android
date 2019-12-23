package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity_ViewBases extends AppCompatActivity { //class to handle the events for the View Bases Activity
    private AdminControlPanel controlPanel; //Object of type admin control panel
    private ArrayList<InventoryModel> inventoryModelArrayList; //List of inventory model objects


    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the control panel object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_bases);

        controlPanel = (AdminControlPanel)getIntent().getSerializableExtra("controlPanel");

        populateList();
    }

    @Override
    protected void onResume(){ //Function to refresh the list when the activity resumes
        super.onResume();

        populateList();
    }

    private void populateList() { //Function to populate the list of bases with values fetched from the database
        ListView listView = (ListView) findViewById(R.id.lstAllBases);
        inventoryModelArrayList = controlPanel.getAllBases();
        CustomControlPanelInventoryArrayAdapter customControlPanelInventoryArrayAdapter = new CustomControlPanelInventoryArrayAdapter(this, R.layout.control_panel_list_item, inventoryModelArrayList);
        listView.setAdapter(customControlPanelInventoryArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_ViewBases.this, Activity_EditBase.class);
                intent.putExtra("base", inventoryModelArrayList.get(position));
                intent.putExtra("controlPanel", controlPanel);
                startActivity(intent);
            }
        });
    }

    public void onClickBtnAdd(View view) { //Event handler for the add button, starts the next activity
        Intent intent = new Intent(Activity_ViewBases.this, Activity_AddBase.class);
        intent.putExtra("controlPanel", controlPanel);
        startActivity(intent);
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
