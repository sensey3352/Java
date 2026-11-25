package com.example.fsm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TestStateMachineTest {

    private final TestStateMachine fsm = new TestStateMachine();

    // Завдання 2: усі функціональні кейси
    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "'',            0",   // порожній рядок
            "abc,           0",
            "T,             1",
            "TE,            2",
            "TES,           3",
            "TEST,          F",
            "abcTESTabc,    F",   // приклад з умови
            "abcTES,        3",   // приклад з умови
            "abcTE,         2",
            "abcT,          1",
            "xxTEST,        F",   // слово в кінці
            "TESTxyz,       F",   // слово на початку
            "TESx,          0",   // обрив у стані 3
            "TEx,           0",   // обрив у стані 2
            "Tx,            0",   // обрив у стані 1
            "test,          0",   // регістр має значення
            "TeST,          1",   // останній символ T -> стан 1
            "TESTTEST,      F",
            "T E S T,       1",   // пробіли між літерами (останній T -> 1)
            "TESTabcT,      F",   // F поглинаючий
    })
    void testFunctionalCases(String input, String expected) {
        assertEquals(expected, fsm.run(input).toString());
    }

    // Завдання 3*: рядки, на яких наївна реалізація не переходила у стан F
    @ParameterizedTest(name = "fixed: \"{0}\" -> F")
    @ValueSource(strings = {"TTEST", "TETEST", "abTTEST", "TETTEST", "TTTEST", "TETETEST", "xTEtESTTEST"})
    void testFixedCasesReachF(String input) {
        assertEquals(State.F, fsm.run(input));
    }

    @ParameterizedTest(name = "naive bug: \"{0}\"")
    @ValueSource(strings = {"TTEST", "TETEST", "abTTEST"})
    void testNaiveImplementationFailsOnTheseCases(String input) {
        assertNotEquals(State.F, new NaiveTestStateMachine().run(input));
    }

    // Проміжні стани після повторних 'T'
    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({"TT, 1", "TTE, 2", "TET, 1", "TETE, 2", "TETES, 3", "TEST T, F"})
    void testRestartOnT(String input, String expected) {
        assertEquals(expected, fsm.run(input).toString());
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> fsm.run(null));
    }
}
