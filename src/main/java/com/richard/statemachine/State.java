package com.richard.statemachine;

public class State {

    private State nextState;

    public State next(State currentState) {
        this.nextState = currentState;
        return this;
    }

    public void run() {

    }
}
