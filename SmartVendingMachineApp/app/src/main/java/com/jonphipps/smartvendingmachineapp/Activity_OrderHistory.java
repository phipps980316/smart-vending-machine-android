package com.jonphipps.smartvendingmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity_OrderHistory extends AppCompatActivity { //class to handle the events for the Order History Activity
    private UserControlPanel controlPanel; //Object to hold the control panel
    private ArrayList<OrderRecordModel> orderRecordModelArrayList; //List of order record model objects

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the control panel object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__order_history);

        controlPanel = (UserControlPanel) getIntent().getSerializableExtra("controlPanel");

        populateList();
    }

    @Override
    protected void onResume() { //Function to refresh the order history list when the activity resumes
        super.onResume();
        populateList();
    }

    private void populateList() { //Function to populate the list of order history with values fetched from the database
        ListView listView = (ListView) findViewById(R.id.lstOrders);
        orderRecordModelArrayList = controlPanel.getUserOrderHistory();
        CustomOrderHistoryArrayAdapter customOrderHistoryArrayAdapter = new CustomOrderHistoryArrayAdapter(this, R.layout.order_history_item, orderRecordModelArrayList);
        listView.setAdapter(customOrderHistoryArrayAdapter);
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
