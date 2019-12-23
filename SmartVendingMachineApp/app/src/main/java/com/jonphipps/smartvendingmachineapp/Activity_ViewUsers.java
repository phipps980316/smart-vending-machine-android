package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity_ViewUsers extends AppCompatActivity { //class to handle the events for the View Topping Activity
    private AdminControlPanel controlPanel; //Object of type admin control panel
    private ArrayList<UserModel> userModelArrayList; //List of user model objects

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the control panel object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_users);

        controlPanel = (AdminControlPanel)getIntent().getSerializableExtra("controlPanel");

        populateList();
    }

    @Override
    protected void onResume() { //Function to refresh the list when the activity resumes
        super.onResume();
        populateList();
    }

    private void populateList() { //Function to populate the list of users with values fetched from the database
        ListView listView = (ListView) findViewById(R.id.lstAllUsers);
        userModelArrayList = controlPanel.getAllUsers();
        CustomControlPanelUserArrayAdapter customControlPanelUserArrayAdapter = new CustomControlPanelUserArrayAdapter(this, R.layout.control_panel_list_item, userModelArrayList);
        listView.setAdapter(customControlPanelUserArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_ViewUsers.this, Activity_EditUser.class);
                intent.putExtra("user", userModelArrayList.get(position));
                intent.putExtra("controlPanel", controlPanel);
                startActivity(intent);
            }
        });
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
