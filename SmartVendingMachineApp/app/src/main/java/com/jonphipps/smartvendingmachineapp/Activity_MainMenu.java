package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Activity_MainMenu extends AppCompatActivity { //class to handle the events for the Main Menu Activity
    private VendingMachine vendingMachine; //Object of type vending machine

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the vending machine object passed from the last activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main_menu);

        vendingMachine = (VendingMachine)getIntent().getSerializableExtra("vendingMachine");
    }

    @Override
    protected void onResume() {//Function to refresh the user's details when the activity resumes
        super.onResume();

        vendingMachine.refreshUser();
    }

    public void onClickBtnDispenser(View view) { //Event handler for the dispenser button, requests a dispenser object to be made and passes it to that next activity that is started
        Dispenser dispenser = vendingMachine.accessDispenser();
        if(dispenser != null)
        {
            Intent intent = new Intent(Activity_MainMenu.this, Activity_SelectBase.class);
            intent.putExtra("dispenser", dispenser);
            startActivity(intent);
        }
        else{
           Toast toast = Toast.makeText(this, "Invalid Permissions", Toast.LENGTH_SHORT);
           toast.show();
        }
    }

    public void onClickBtnControlPanel(View view) { //Event handler for the control panel button, requests a control panel object to be made and passes it to that next activity that is started depending on the user's role
        UserControlPanel controlPanel = vendingMachine.accessControlPanel();
        if(controlPanel != null && controlPanel.getUser().getRole().equals("admin")) {
            Intent intent = new Intent(Activity_MainMenu.this, Activity_ControlPanelAdminMenu.class);
            intent.putExtra("controlPanel", controlPanel);
            startActivity(intent);
        }
        else if(controlPanel != null && controlPanel.getUser().getRole().equals("user")) {
            Intent intent = new Intent(Activity_MainMenu.this, Activity_ControlPanelUserMenu.class);
            intent.putExtra("controlPanel", controlPanel);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(this, "Invalid Permissions", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickBtnLogOut(View view) {//Event handler for the log out button, logs out of the vending machine and ends the current activity
        vendingMachine.logout();
        finish();
    }
}
