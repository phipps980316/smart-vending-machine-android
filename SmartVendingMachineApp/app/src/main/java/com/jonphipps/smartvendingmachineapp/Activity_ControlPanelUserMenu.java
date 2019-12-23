package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_ControlPanelUserMenu extends AppCompatActivity { //class to handle the events for the Control Panel User Menu Activity
    private UserControlPanel controlPanel; //Object to hold the control panel

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the control panel object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__control_panel_user_menu);

        controlPanel = (UserControlPanel)getIntent().getSerializableExtra("controlPanel");
    }

    public void onClickBtnViewOrderHistory(View view) { //Event handler for the view order history button, serializes the control panel object to pass to the next activity and starts the Order History activity
        Intent intent = new Intent(Activity_ControlPanelUserMenu.this, Activity_OrderHistory.class);
        intent.putExtra("controlPanel", controlPanel);
        startActivity(intent);
    }

    public void onClickBtnEditProfile(View view) { //Event handler for the edit profile button, serializes the control panel object to pass to the next activity and starts the Edit Profile activity
        Intent intent = new Intent(Activity_ControlPanelUserMenu.this, Activity_EditProfile.class);
        intent.putExtra("controlPanel", controlPanel);
        startActivity(intent);
        finish();
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
