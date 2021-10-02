package com.richard.statemachine;

import java.util.Objects;
import java.util.Optional;

public class Transition {
    private boolean finalState = false;
    private State initialState;
    private State nextState;
    private Event eventTrigger;

    public Transition() {}

    public Transition(State initialState) {
        Objects.requireNonNull(initialState);
        this.initialState = initialState;
    }

    public Transition next(State nextState) {
        this.nextState = nextState;
        return this;
    }

    public Transition on(Event event) {
        this.eventTrigger = event;
        return this;
    }

    public Transition finalState() {
        this.finalState = true;
        return this;
    }

    public State getStartingState() {
        return initialState;
    }

    public Optional<State> getNextState() {
        if (this.finalState) {
            return Optional.empty();
        }
        return Optional.ofNullable(nextState);
    }

    public Event getEvent() {
        return this.eventTrigger;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Start " + this.initialState);
        if (this.finalState) {
            builder.append("\n>>> Final");
        } else {
            builder.append("\nNext: " + this.nextState).append("\nEvent: " + this.eventTrigger);
        }

        return builder.toString();
    }
}
