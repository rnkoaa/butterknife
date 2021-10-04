package com.richard.statemachine;

public interface StateMachineListener {

    void stateChanged(State from, State to);
}
