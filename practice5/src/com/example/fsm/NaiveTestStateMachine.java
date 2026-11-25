package com.example.fsm;

/**
 * Початкова (наївна) реалізація автомата — містить помилку (завдання 3*).
 * При неспівпадінні символу автомат завжди повертається в стан 0 і "забуває",
 * що поточний символ 'T' може бути початком нового слова "TEST".
 * Рядки, на яких вона помиляється: "TTEST", "TETEST", "abTTEST", "TETTEST" тощо.
 */
public class NaiveTestStateMachine {
    public State run(String input) {
        State state = State.S0;
        for (char c : input.toCharArray()) {
            switch (state) {
                case S0: state = (c == 'T') ? State.S1 : State.S0; break;
                case S1: state = (c == 'E') ? State.S2 : State.S0; break; // БАГ: 'T' -> має бути S1
                case S2: state = (c == 'S') ? State.S3 : State.S0; break; // БАГ: 'T' -> має бути S1
                case S3: state = (c == 'T') ? State.F  : State.S0; break;
                case F:  return State.F;
            }
        }
        return state;
    }
}
