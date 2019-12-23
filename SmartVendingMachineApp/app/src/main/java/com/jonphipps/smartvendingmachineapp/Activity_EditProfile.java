package com.jonphipps.smartvendingmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_EditProfile extends AppCompatActivity { //class to handle the events for the Edit Profile Activity
    private UserControlPanel controlPanel; //Object to hold the control panel

    private EditText txtPassword; //Object to reference the txtPassword text field

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity and deserialize the control panel object passed from the last activity
                                                        //Function also populates the activity edit text fields with the user's information
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit_profile);

        controlPanel = (UserControlPanel)getIntent().getSerializableExtra("controlPanel");

        EditText txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtUsername.setText(controlPanel.getUser().getName());
        txtUsername.setEnabled(false);

        txtPassword = (EditText)findViewById(R.id.txtPassword);
    }

    public void onClickBtnUpdate(View view) { //Event handler for the update button, allows a user to change their password
        if(!txtPassword.getText().toString().isEmpty() ) {
            controlPanel.getUser().setHash(controlPanel.MD5Hash(txtPassword.getText().toString(), controlPanel.getUser().getSalt()));
            controlPanel.updateAccount();
            finish();
        }
        else {
            Toast toast = Toast.makeText(this, "Nothing Changed", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickBtnBack(View view) { //Event handler for the back button, allows the current activity to end and return to the last one on the stack
        finish();
    }
}
