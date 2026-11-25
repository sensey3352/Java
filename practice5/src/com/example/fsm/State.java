package com.example.fsm;

/** Стани автомата: 0 (нічого), 1 ("T"), 2 ("TE"), 3 ("TES"), F ("TEST" знайдено). */
public enum State {
    S0("0"), S1("1"), S2("2"), S3("3"), F("F");

    private final String label;

    State(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
