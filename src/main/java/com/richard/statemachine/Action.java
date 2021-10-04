package com.richard.statemachine;

@FunctionalInterface
public interface Action<T, U> {

    void apply(StateContext<T, U> ctx);
}
