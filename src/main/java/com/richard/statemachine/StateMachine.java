package com.richard.statemachine;

import java.util.List;

public class StateMachine {
    private List<State> states;
    private final State initialState;
    private final State currentState;

    public StateMachine(State initialState){
        this.initialState = initialState;
    }

    void runAll(){
        for (State state : states ) {
            this.currentState = currentState.next(state)
           this.currentState.run()
        }

    }

}
