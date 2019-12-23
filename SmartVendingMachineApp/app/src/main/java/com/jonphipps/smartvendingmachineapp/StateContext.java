package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;
import java.util.Vector;

public class StateContext implements Serializable { //Class to represent the context of the machine
    protected State currentState = null; //Object to hold the current state
    protected int stateID; //Integer to hold the current stateID
    protected Vector<State> availableStates = new Vector<>(); //Vector to hold all of the available states

    public void setState(int newState) { //Function to change the state of the machine
        this.currentState = availableStates.get(newState);
        this.stateID = newState;
    }
    public int getStateID() {
        return this.stateID;
    }
}
