package com.example.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private final Calculator calc = new Calculator();

    @Test
    void testBasicOperations() throws InvalidInputException {
        assertEquals(5, calc.add(2, 3));
        assertEquals(-1, calc.subtract(2, 3));
        assertEquals(6, calc.multiply(2, 3));
        assertEquals(2.5, calc.divide(5, 2));
        assertEquals(3, calc.sqrt(9));
    }

    // Ділення на нуль
    @Test
    void testDivisionByZero() {
        ArithmeticException ex = assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
        assertEquals("Ділення на нуль неможливе", ex.getMessage());
    }

    @Test
    void testDivisionByZeroThroughCalculate() {
        assertThrows(ArithmeticException.class, () -> calc.calculate(1, 0, "/"));
    }

    // Невірні вхідні дані (рядки замість чисел)
    @Test
    void testStringInsteadOfNumber() {
        assertThrows(InvalidInputException.class, () -> calc.parseNumber("abc"));
        assertThrows(InvalidInputException.class, () -> calc.parseNumber("12a"));
        assertThrows(InvalidInputException.class, () -> calc.parseNumber(""));
        assertThrows(InvalidInputException.class, () -> calc.parseNumber(null));
    }

    @Test
    void testValidNumberStrings() throws InvalidInputException {
        assertEquals(3.5, calc.parseNumber("3.5"));
        assertEquals(3.5, calc.parseNumber(" 3,5 "));
        assertEquals(-7, calc.parseNumber("-7"));
    }

    // Власне виключення
    @Test
    void testSqrtOfNegativeThrowsCustomException() {
        InvalidInputException ex = assertThrows(InvalidInputException.class, () -> calc.sqrt(-4));
        assertTrue(ex.getMessage().contains("від'ємного"));
    }

    @Test
    void testNaNAndInfinityThrowCustomException() {
        assertThrows(InvalidInputException.class, () -> calc.add(Double.NaN, 1));
        assertThrows(InvalidInputException.class, () -> calc.multiply(Double.POSITIVE_INFINITY, 2));
    }

    @Test
    void testUnknownOperation() {
        assertThrows(InvalidInputException.class, () -> calc.calculate(1, 2, "%"));
    }
}
