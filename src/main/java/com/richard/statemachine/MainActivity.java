package com.richard.statemachine;

/** Main Application endpoint */
public class MainActivity {

    public static void main(String[] args) {
        var statemachine =
                new StateMachine(new State("idle"))
                        .registerListener(
                                new StateMachineListener() {
                                    public void stateChanged(State from, State to) {
                                        System.out.println("From state: " + from + " To: " + to);
                                    }
                                })
                        .withFinalState(new State("success"));

        statemachine.addTransition(
                new Transition(new State("idle")).target(new State("loading")).on(Event.SUBMIT));

        // statemachine.addTransition(
        //         new Transition(new State("loading"))
        //                 .next(new State("error"))
        //                 .on(Event.PAYMENT_FAILED));

        statemachine.addTransition(
                new Transition(new State("loading"))
                        .target(new State("error"))
                        .on(Event.PAYMENT_FAILED)
                // .action(ctx -> {
                // System.out.println("Applying Action")
                //     return this;
                // })
                );

        statemachine.addTransition(
                new Transition(new State("loading"))
                        .target(new State("success"))
                        .on(Event.PAYMENT_RECEIVED));

        statemachine.addTransition(
                new Transition(new State("error")).target(new State("loading")).on(Event.SUBMIT));

        statemachine.addTransition(new Transition(new State("success")).finalState());

        statemachine.send(Event.SUBMIT);
        System.out.println(statemachine);
        System.out.println("========================================================");

        statemachine.send(Event.PAYMENT_FAILED);
        System.out.println(statemachine);
        System.out.println("========================================================");

        statemachine.send(Event.SUBMIT);
        System.out.println(statemachine);
        System.out.println("========================================================");

        statemachine.send(Event.PAYMENT_RECEIVED);
        System.out.println(statemachine);
        System.out.println("========================================================");
    }
}
