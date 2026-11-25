package com.example.finance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Аналіз транзакцій.
 * Абстрактний клас зі статичними методами: список транзакцій передається параметром,
 * тому окремий об'єкт аналізатора створювати не потрібно.
 */
public abstract class TransactionAnalyzer {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter MONTH = DateTimeFormatter.ofPattern("MM-yyyy");
    private static final DateTimeFormatter MONTH_KEY = DateTimeFormatter.ofPattern("yyyy-MM");

    private static LocalDate dateOf(Transaction t) {
        return LocalDate.parse(t.getDate(), DATE);
    }

    // Задача 2 — загальний баланс
    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    // Задача 3 — кількість транзакцій за місяць ("MM-yyyy")
    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            if (dateOf(transaction).format(MONTH).equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    // Задача 5 — 10 найбільших витрат
    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    // ---------- Самостійна робота ----------

    private static List<Transaction> expensesInPeriod(List<Transaction> transactions, LocalDate from, LocalDate to) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> !dateOf(t).isBefore(from) && !dateOf(t).isAfter(to))
                .collect(Collectors.toList());
    }

    /** Найбільша витрата (за модулем) за період [from; to]. */
    public static Optional<Transaction> findLargestExpense(List<Transaction> transactions, LocalDate from, LocalDate to) {
        return expensesInPeriod(transactions, from, to).stream().min(Comparator.comparing(Transaction::getAmount));
    }

    /** Найменша витрата (за модулем) за період [from; to]. */
    public static Optional<Transaction> findSmallestExpense(List<Transaction> transactions, LocalDate from, LocalDate to) {
        return expensesInPeriod(transactions, from, to).stream().max(Comparator.comparing(Transaction::getAmount));
    }

    /** Сумарні витрати по категоріях (категорія = опис транзакції). */
    public static Map<String, Double> expensesByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(Transaction::getDescription, TreeMap::new,
                        Collectors.summingDouble(t -> -t.getAmount())));
    }

    /** Сумарні витрати по місяцях (ключ "yyyy-MM"). */
    public static Map<String, Double> expensesByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(t -> dateOf(t).format(MONTH_KEY), TreeMap::new,
                        Collectors.summingDouble(t -> -t.getAmount())));
    }

    /** Категорія, на яку витрачено найбільше. */
    public static Optional<Map.Entry<String, Double>> findTopCategory(List<Transaction> transactions) {
        return expensesByCategory(transactions).entrySet().stream().max(Map.Entry.comparingByValue());
    }
}
