package com.example.finance;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = args.length > 0 ? args[0] : "https://informer.com.ua/dut/java/pr2.csv";

        // Усі три класи абстрактні — викликаємо статичні методи без створення об'єктів
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        // Задача 1 — виведення вмісту файлу
        System.out.println("=== Транзакції (" + transactions.size() + ") ===");
        transactions.forEach(System.out::println);
        if (transactions.isEmpty()) return;

        System.out.println();
        // Задача 2
        TransactionReportGenerator.printBalanceReport(TransactionAnalyzer.calculateTotalBalance(transactions));

        // Задача 3
        String monthYear = "01-2024";
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear,
                TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear));

        // Задача 5
        System.out.println();
        TransactionReportGenerator.printTopExpensesReport(TransactionAnalyzer.findTopExpenses(transactions));

        // Самостійна робота
        System.out.println();
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 3, 31);
        TransactionReportGenerator.printMinMaxExpense(from + " – " + to,
                TransactionAnalyzer.findLargestExpense(transactions, from, to),
                TransactionAnalyzer.findSmallestExpense(transactions, from, to));

        System.out.println();
        TransactionAnalyzer.findTopCategory(transactions).ifPresent(e ->
                System.out.printf("Найбільше витрачено на: %s (%.2f грн)%n", e.getKey(), e.getValue()));

        System.out.println();
        TransactionReportGenerator.printChart("Витрати по категоріях", TransactionAnalyzer.expensesByCategory(transactions));
        System.out.println();
        TransactionReportGenerator.printChart("Витрати по місяцях", TransactionAnalyzer.expensesByMonth(transactions));
    }
}
