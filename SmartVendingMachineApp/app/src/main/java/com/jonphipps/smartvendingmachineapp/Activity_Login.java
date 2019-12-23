package com.jonphipps.smartvendingmachineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Login extends AppCompatActivity { //class to handle the events for the Login Activity
    private VendingMachine vendingMachine = new VendingMachine(); //Object of type vending machine created

    private EditText txtUsername; //Object to reference txtUsername textfield
    private EditText txtPassword; //Object to reference txtPassword textfield

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Function to display the current activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }

    public void onClickBtnLogin(View view) { //Event handler for the login button, allows the user to login given valid credentials are entered
                                            //if valid credentials are entered, the vending machine object is serialized and passed to the next activity which is started
        if(!txtUsername.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty())
        {
            if (vendingMachine.login(txtUsername.getText().toString(), txtPassword.getText().toString())) {
                Intent intent = new Intent(Activity_Login.this, Activity_MainMenu.class);
                intent.putExtra("vendingMachine", vendingMachine);
                startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {
            Toast toast = Toast.makeText(this, "All Fields Required", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickBtnRegister(View view) { //Event handler for the register button, allows the user to register an account on the machine
                                                //when valid values are enters, the vending machine object is serialized and passed to the next activity which is started
        if(!txtUsername.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty())
        {
            if (vendingMachine.registerUser(txtUsername.getText().toString(), txtPassword.getText().toString())) {
                vendingMachine.login(txtUsername.getText().toString(), txtPassword.getText().toString());
                Intent intent = new Intent(Activity_Login.this, Activity_MainMenu.class);
                intent.putExtra("vendingMachine", vendingMachine);
                startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(this, "Username Already Taken", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}

