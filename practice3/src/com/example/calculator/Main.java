package com.example.calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВведіть операцію (+, -, *, /, sqrt) або 'exit' для виходу:");
            if (!scanner.hasNextLine()) break;
            String op = scanner.nextLine().trim();
            if (op.equalsIgnoreCase("exit")) {
                System.out.println("До побачення!");
                break;
            }

            try {
                System.out.println("Введіть перше число:");
                double a = calculator.parseNumber(scanner.nextLine());
                double b = 0;
                if (!op.equals("sqrt")) {
                    System.out.println("Введіть друге число:");
                    b = calculator.parseNumber(scanner.nextLine());
                }
                System.out.println("Результат: " + calculator.calculate(a, b, op));
            } catch (ArithmeticException e) {
                System.out.println("Арифметична помилка: " + e.getMessage());
            } catch (InvalidInputException e) {
                System.out.println("Помилка вводу: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Непередбачена помилка: " + e.getMessage());
            } finally {
                System.out.println("Обробку запиту завершено.");
            }
        }
    }
}
