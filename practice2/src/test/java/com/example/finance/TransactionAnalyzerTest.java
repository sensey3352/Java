package com.example.finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class TransactionAnalyzerTest {

    // Задача 2.2
    @Test
    public void testCalculateTotalBalance() {
        // Створення тестових даних
        Transaction transaction1 = new Transaction("01-01-2023", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("02-01-2023", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("03-01-2023", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        // Виклик статичного методу, який потрібно протестувати
        double result = TransactionAnalyzer.calculateTotalBalance(transactions);

        // Перевірка результату
        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    // Задача 3.2
    @Test
    public void testCountTransactionsByMonth() {
        Transaction transaction1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        int countFeb = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023");

        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
    }

    // Самостійна робота: тест 10 найбільших витрат
    @Test
    public void testFindTopExpenses() {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            transactions.add(new Transaction("01-01-2024", -i * 100.0, "Витрата " + i));
        }
        transactions.add(new Transaction("01-01-2024", 99999.0, "Дохід"));

        List<Transaction> top = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(10, top.size());
        Assertions.assertEquals(-1500.0, top.get(0).getAmount());
        Assertions.assertEquals(-600.0, top.get(9).getAmount());
        Assertions.assertTrue(top.stream().allMatch(t -> t.getAmount() < 0));
    }

    @Test
    public void testFindTopExpensesWhenFewerThanTen() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2024", -10, "A"),
                new Transaction("02-01-2024", 500, "B"));
        Assertions.assertEquals(1, TransactionAnalyzer.findTopExpenses(transactions).size());
    }

    @Test
    public void testLargestAndSmallestExpenseInPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-01-2024", -300, "A"),
                new Transaction("10-01-2024", -50, "B"),
                new Transaction("15-02-2024", -5000, "C"),  // поза періодом
                new Transaction("20-01-2024", 1000, "D"));  // дохід
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 31);

        Assertions.assertEquals("A", TransactionAnalyzer.findLargestExpense(transactions, from, to).get().getDescription());
        Assertions.assertEquals("B", TransactionAnalyzer.findSmallestExpense(transactions, from, to).get().getDescription());
    }

    @Test
    public void testExpensesByCategoryAndMonth() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-01-2024", -300, "Сільпо"),
                new Transaction("10-01-2024", -200, "Сільпо"),
                new Transaction("15-02-2024", -1000, "АТБ"),
                new Transaction("20-01-2024", 5000, "Зарплата"));

        Map<String, Double> byCat = TransactionAnalyzer.expensesByCategory(transactions);
        Assertions.assertEquals(500.0, byCat.get("Сільпо").doubleValue());
        Assertions.assertEquals(1000.0, byCat.get("АТБ").doubleValue());
        Assertions.assertFalse(byCat.containsKey("Зарплата"));

        Map<String, Double> byMonth = TransactionAnalyzer.expensesByMonth(transactions);
        Assertions.assertEquals(500.0, byMonth.get("2024-01").doubleValue());
        Assertions.assertEquals(1000.0, byMonth.get("2024-02").doubleValue());
    }
}
