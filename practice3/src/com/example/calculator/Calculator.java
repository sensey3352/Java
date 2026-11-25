package com.example.calculator;

/** Калькулятор з базовими операціями та перевіркою вхідних даних. */
public class Calculator {

    /** Перевірка, що число коректне (не NaN і не нескінченність). */
    private void validate(double... values) throws InvalidInputException {
        for (double v : values) {
            if (Double.isNaN(v) || Double.isInfinite(v)) {
                throw new InvalidInputException("Некоректне числове значення: " + v);
            }
        }
    }

    public double add(double a, double b) throws InvalidInputException {
        validate(a, b);
        return a + b;
    }

    public double subtract(double a, double b) throws InvalidInputException {
        validate(a, b);
        return a - b;
    }

    public double multiply(double a, double b) throws InvalidInputException {
        validate(a, b);
        return a * b;
    }

    public double divide(double a, double b) throws InvalidInputException {
        validate(a, b);
        if (b == 0) {
            // для double ділення на 0 саме по собі не кидає виключення, тому кидаємо явно
            throw new ArithmeticException("Ділення на нуль неможливе");
        }
        return a / b;
    }

    public double sqrt(double a) throws InvalidInputException {
        validate(a);
        if (a < 0) {
            throw new InvalidInputException("Квадратний корінь з від'ємного числа (" + a + ") не існує");
        }
        return Math.sqrt(a);
    }

    /** Перетворення рядка, введеного користувачем, у число. */
    public double parseNumber(String text) throws InvalidInputException {
        if (text == null || text.trim().isEmpty()) {
            throw new InvalidInputException("Порожнє значення замість числа");
        }
        try {
            return Double.parseDouble(text.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + text + "' не є числом", e);
        }
    }

    /** Виконання операції за її символом. */
    public double calculate(double a, double b, String operation) throws InvalidInputException {
        switch (operation.trim()) {
            case "+": return add(a, b);
            case "-": return subtract(a, b);
            case "*": return multiply(a, b);
            case "/": return divide(a, b);
            case "sqrt": return sqrt(a);
            default:
                throw new InvalidInputException("Невідома операція: " + operation);
        }
    }
}
