package com.richard.statemachine;

public class MainActivity {

    public static void main(String[] args) {
        var statemachine = new StateMachine(new State("idle")).withFinalState(new State("success"));

        statemachine.addTransition(
                new Transition(new State("idle")).next(new State("loading")).on(Event.SUBMIT));

        statemachine.addTransition(
                new Transition(new State("loading"))
                        .next(new State("error"))
                        .on(Event.PAYMENT_FAILED));

        statemachine.addTransition(
                new Transition(new State("loading"))
                        .next(new State("success"))
                        .on(Event.PAYMENT_RECEIVED));

        statemachine.addTransition(
                new Transition(new State("error")).next(new State("loading")).on(Event.SUBMIT));

        statemachine.addTransition(new Transition(new State("success")).finalState());

        statemachine.send(Event.SUBMIT);
        System.out.println(statemachine);

        statemachine.send(Event.PAYMENT_FAILED);
        System.out.println(statemachine);

        statemachine.send(Event.SUBMIT);
        System.out.println(statemachine);

        statemachine.send(Event.PAYMENT_RECEIVED);
        System.out.println(statemachine);
    }
}
