package com.example.fsm;

/**
 * Виправлена реалізація автомата.
 * Якщо символ не продовжує слово, але сам є 'T' — переходимо в стан 1,
 * бо цей символ може бути початком "TEST". Стан F — поглинаючий.
 */
public class TestStateMachine {

    /** Один крок автомата: (стан, символ) -> новий стан. */
    public State next(State state, char c) {
        switch (state) {
            case S0: return c == 'T' ? State.S1 : State.S0;
            case S1: return c == 'E' ? State.S2 : (c == 'T' ? State.S1 : State.S0);
            case S2: return c == 'S' ? State.S3 : (c == 'T' ? State.S1 : State.S0);
            case S3: return c == 'T' ? State.F  : State.S0;
            case F:
            default: return State.F;
        }
    }

    /** Обробка всього рядка, повертає кінцевий стан. */
    public State run(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Вхідний рядок не може бути null");
        }
        State state = State.S0;
        for (char c : input.toCharArray()) {
            state = next(state, c);
            if (state == State.F) {
                break; // далі стан не зміниться
            }
        }
        return state;
    }
}
