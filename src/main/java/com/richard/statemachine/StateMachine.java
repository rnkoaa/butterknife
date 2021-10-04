package com.richard.statemachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StateMachine {
    private final UUID id;
    private State currentState;
    private State finalState;
    private final StateContext<State, State> stateContext;
    private StateMachineListener listener;
    private Map<Event, List<Transition>> transitionTable;
    private boolean complete;
    private boolean active;

    public StateMachine(State initialState) {
        this.id = UUID.randomUUID();
        this.currentState = initialState;
        this.transitionTable = new HashMap<>();
        this.stateContex = new StateContext<>();
    }

    public StateMachine registerListener(StateMachineListener listener) {
        this.listener = listener;
        return this;
    }

    /** add a new state to the states */
    void addTransition(Transition transition) {
        List<Transition> eventTransitions =
                transitionTable.computeIfAbsent(transition.getEvent(), (e) -> new ArrayList<>());
        eventTransitions.add(transition);
        transitionTable.put(transition.getEvent(), eventTransitions);
    }

    void finalizedBy() {
        System.out.printf(
                "Finalize[FinalState: %s, CurrentState: %s]\n", this.finalState, this.currentState);
        this.complete = (currentState.equals(finalState));
    }

    void send(Event event) {
        var transitions = transitionTable.get(event);
        if (transitions == null || transitions.size() == 0) {
            throw new IllegalArgumentException("no transitions defined for this event " + event);
        }

        if (transitions.size() == 1) {
            var transition = transitions.get(0);
            if (!transition.getStartingState().equals(this.currentState)) {
                System.out.printf(
                        "Transition initialState: %s, CurrentState: %s\n",
                        transition.getStartingState(), currentState);
                return;
            }

            if (transition.getTarget().isEmpty()) {
                System.out.printf(
                        "Transition initialState: %s, CurrentState: %s\n",
                        transition.getStartingState(), currentState);
                return;
            }

            State target = transition.getTarget().get();
            if (listener != null) {
                listener.stateChanged(this.currentState, target);
            }

            System.out.println("Exiting Current State: " + this.currentState);
            System.out.println("Entering Next State: " + target);

            // transition exists, so update current state
            this.currentState = target;
            System.out.println("Executing Current state '" + this.currentState + "' Action ");

            finalizedBy();
        }

        var maybeStartingState =
                transitions.stream()
                        .filter(it -> it.getStartingState().equals(this.currentState))
                        .findFirst();

        maybeStartingState.ifPresent(
                transition -> {
                    transition
                            .getTarget()
                            .ifPresentOrElse(
                                    nextState -> {
                                        // transition exists, so update current state
                                        if (listener != null) {
                                            listener.stateChanged(this.currentState, nextState);
                                        }
                                        System.out.println(
                                                "Exiting Current State: " + this.currentState);
                                        System.out.println("Entering Next State: " + nextState);

                                        this.currentState = nextState;
                                        System.out.println(
                                                "Executing Current state '"
                                                        + this.currentState
                                                        + "' Action ");
                                        finalizedBy();
                                    },
                                    () -> {
                                        System.out.println("Transition is final");
                                        finalizedBy();
                                    });
                });
    }

    public StateMachine withFinalState(State state) {
        this.finalState = state;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("StateMachine");
        builder.append("\nId: ").append(id.toString());
        builder.append("\nCurrentState: ").append(currentState);
        builder.append("\nStatus: ").append(complete ? "complete" : "In Progress");
        return builder.toString();
    }
}
