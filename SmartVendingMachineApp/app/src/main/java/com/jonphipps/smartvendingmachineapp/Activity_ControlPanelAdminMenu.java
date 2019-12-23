package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_ControlPanelAdminMenu extends AppCompatActivity { //class to handle the events for the Control Panel Admin Menu Activity
    private AdminControlPanel controlPanel; //Object to hold the control panel

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the control panel object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__control_panel_admin_menu);

        controlPanel = (AdminControlPanel)getIntent().getSerializableExtra("controlPanel");
    }

    public void onClickBtnViewOrderHistory(View view) { //Event handler for the view order history button, serializes the control panel object to pass to the next activity and starts the Order History activity
        Intent intent = new Intent(Activity_ControlPanelAdminMenu.this, Activity_OrderHistory.class);
        intent.putExtra("controlPanel", controlPanel);
        startActivity(intent);
    }

    public void onClickBtnViewSalesData(View view) { //Event handler for the view sales data button, serializes the control panel object to pass to the next activity and starts the View Sales Data activity
        Intent intent = new Intent( Activity_ControlPanelAdminMenu.this, Activity_ViewSalesData.class);
        intent.putExtra("controlPanel", controlPanel);
        startActivity(intent);
    }

    public void onClickBtnViewUsers(View view) { //Event handler for the view users button, serializes the control panel object to pass to the next activity and starts the View Users activity
        Intent intent = new Intent(Activity_ControlPanelAdminMenu.this, Activity_ViewUsers.class);
        intent.putExtra("controlPanel", controlPanel);
        startActivity(intent);
    }

    public void onClickBtnViewBases(View view) { //Event handler for the view bases button, serializes the control panel object to pass to the next activity and starts the View Bases activity
        Intent intent = new Intent(Activity_ControlPanelAdminMenu.this, Activity_ViewBases.class);
        intent.putExtra("controlPanel", controlPanel);
        startActivity(intent);
    }

    public void onClickBtnViewToppings(View view) { //Event handler for the view toppings button, serializes the control panel object to pass to the next activity and starts the View Toppings activity
        Intent intent = new Intent(Activity_ControlPanelAdminMenu.this, Activity_ViewToppings.class);
        intent.putExtra("controlPanel", controlPanel);
        startActivity(intent);
    }

    public void onClickBtnEditProfile(View view) { //Event handler for the edit profile button, serializes the control panel object to pass to the next activity and starts the Edit Profile activity
        Intent intent = new Intent(Activity_ControlPanelAdminMenu.this, Activity_EditProfile.class);
        intent.putExtra("controlPanel", controlPanel);
        startActivity(intent);
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
