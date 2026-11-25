package com.example.finance;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Задача 4 — виведення звітів.
 * Абстрактний клас зі статичними методами, екземпляр не створюється.
 */
public abstract class TransactionReportGenerator {

    /** Скільки грн відповідає одному символу '*' за замовчуванням. */
    public static final double DEFAULT_UNIT = 1000;

    public static void printBalanceReport(double totalBalance) {
        System.out.printf("Загальний баланс: %.2f%n", totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDate() + "  " + expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printMinMaxExpense(String period, Optional<Transaction> max, Optional<Transaction> min) {
        System.out.println("Витрати за період " + period + ":");
        System.out.println("  Найбільша: " + max.map(Transaction::toString).orElse("немає витрат"));
        System.out.println("  Найменша:  " + min.map(Transaction::toString).orElse("немає витрат"));
    }

    /** Рядок із зірочок: одна '*' = unit грн (мінімум одна для ненульової суми). */
    public static String stars(double amount, double unit) {
        int n = (int) Math.round(amount / unit);
        if (n == 0 && amount > 0) n = 1;
        return "*".repeat(n);
    }

    public static String stars(double amount) {
        return stars(amount, DEFAULT_UNIT);
    }

    public static void printChart(String title, Map<String, Double> data) {
        printChart(title, data, DEFAULT_UNIT);
    }

    public static void printChart(String title, Map<String, Double> data, double unit) {
        System.out.println(title + " (1 * = " + (int) unit + " грн):");
        int width = data.keySet().stream().mapToInt(String::length).max().orElse(10);
        for (Map.Entry<String, Double> e : data.entrySet()) {
            System.out.printf("  %-" + width + "s | %-30s %.2f%n", e.getKey(), stars(e.getValue(), unit), e.getValue());
        }
    }
}
