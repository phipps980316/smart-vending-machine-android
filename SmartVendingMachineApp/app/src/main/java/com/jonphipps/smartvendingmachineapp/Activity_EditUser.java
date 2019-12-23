package com.jonphipps.smartvendingmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class Activity_EditUser extends AppCompatActivity { //class to handle the events for the Edit User Activity
    private AdminControlPanel controlPanel; //Object to hold the control panel
    private UserModel user; //Object to hold the user

    private RadioButton rdbUser; //Object to reference the rdbuser radio button
    private RadioButton rdbAdmin; //Object to reference the rdbadmin radio button

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the user and control panel objects passed from the last activity
                                                        //Function also populates the activity edit text fields with the user's details
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit_user);

        user = (UserModel)getIntent().getSerializableExtra("user");
        controlPanel = (AdminControlPanel)getIntent().getSerializableExtra("controlPanel");

        EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtUsername.setText(user.getName());
        txtUsername.setEnabled(false);

        rdbUser = (RadioButton)findViewById(R.id.rdbUser);
        rdbAdmin = (RadioButton)findViewById(R.id.rdbAdmin);

        Button btnUpdate = (Button)findViewById(R.id.btnUpdate);
        Button btnDelete = (Button)findViewById(R.id.btnDelete);

        if(user.getRole().equals("user")) {
            rdbUser.setChecked(true);
        }
        else if(user.getRole().equals("admin")) {
            rdbAdmin.setChecked(true);
        }

        if (user.getId() == controlPanel.getUser().getId()) {
            rdbUser.setEnabled(false);
            rdbAdmin.setEnabled(false);
            btnDelete.setEnabled(false);
            btnUpdate.setEnabled(false);
        }
    }

    public void onClickBtnUpdate(View view) { //Event handler for the update button, allows an admin to change a user's role by calling the edit user function in the control panel
        if (rdbUser.isChecked()) {
            user.setRole("user");
        }
        else if (rdbAdmin.isChecked()) {
            user.setRole("admin");
        }

        controlPanel.updateUser(user);
        finish();
    }

    public void onClickBtnDelete(View view) { //Event handler for the delete button, allows a topping to be deleted by calling the control panel's delete user function
        controlPanel.deleteUser(user);
        finish();
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
