package com.example.fsm;

public class Main {
    public static void main(String[] args) {
        TestStateMachine fsm = new TestStateMachine();
        NaiveTestStateMachine naive = new NaiveTestStateMachine();

        String[] inputs = args.length > 0 ? args
                : new String[]{"abcTESTabc", "abcTES", "TEST", "", "abc", "T", "TE",
                               "test", "TTEST", "TETEST", "abTTEST"};

        System.out.printf("%-14s %-12s %-10s%n", "Рядок", "Виправлений", "Наївний");
        for (String s : inputs) {
            System.out.printf("%-14s %-12s %-10s%n", "\"" + s + "\"", fsm.run(s), naive.run(s));
        }
    }
}
