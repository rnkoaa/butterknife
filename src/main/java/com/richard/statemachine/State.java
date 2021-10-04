package com.richard.statemachine;

/*
 * State - a state in the class
 */
public record State(String name) {

    void onExit(StateContext<State, State> ctx) {
        System.out.println("State '" + name + "' exiting");
    }

    void onEntry(StateContext<State, State> ctx) {
        System.out.println("State '" + name + "' exiting");
    }
}
