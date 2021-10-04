package com.richard.statemachine;

import java.util.Objects;
import java.util.Optional;

public class Transition {
    private boolean finalState = false;
    private State source;
    private State target;
    private Event eventTrigger;

    public Transition() {}

    public Transition(State initialState) {
        Objects.requireNonNull(initialState);
        this.source = initialState;
    }

    /** Transition to the next state */
    public Transition target(State target) {
        this.target = target;
        return this;
    }

    public Transition on(Event event) {
        this.eventTrigger = event;
        return this;
    }

    // public Transition action(Action<EventContext> ctx) {
    //     // ctx.apply()

    //     return this;
    // }

    public Transition finalState() {
        this.finalState = true;
        return this;
    }

    public State getStartingState() {
        return source;
    }

    public Optional<State> getTarget() {
        if (this.finalState) {
            return Optional.empty();
        }
        return Optional.ofNullable(target);
    }

    public Event getEvent() {
        return this.eventTrigger;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Start " + this.source);
        if (this.finalState) {
            builder.append("\n>>> Final");
        } else {
            builder.append("\nNext: " + this.target).append("\nEvent: " + this.eventTrigger);
        }

        return builder.toString();
    }
}
