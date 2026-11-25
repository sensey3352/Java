package com.example.finance;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Читання транзакцій з CSV.
 * Абстрактний клас зі статичними методами: екземпляр не потрібен і не може бути створений,
 * виклик — TransactionCSVReader.readTransactions(...).
 */
public abstract class TransactionCSVReader {

    /** Парсер за замовчуванням: рядок "дата,сума,опис". */
    private static final TransactionParser DEFAULT_PARSER = new CsvTransactionParser();

    /** filePath — URL (http...) або шлях до локального файлу. */
    public static List<Transaction> readTransactions(String filePath) {
        DataSource source = filePath.startsWith("http")
                ? new UrlDataSource(filePath)
                : new FileDataSource(filePath);
        return readTransactions(source);
    }

    public static List<Transaction> readTransactions(DataSource source) {
        return readTransactions(source, DEFAULT_PARSER);
    }

    /** Читання (DataSource — звідки) відокремлене від обробки (TransactionParser — як парсити). */
    public static List<Transaction> readTransactions(DataSource source, TransactionParser parser) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(source.open())) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("﻿", "").trim(); // прибрати BOM
                if (line.isEmpty()) continue;
                try {
                    transactions.add(parser.parse(line));
                } catch (IllegalArgumentException e) {
                    // заголовок або пошкоджений рядок — пропускаємо
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка читання даних: " + e.getMessage());
        }
        return transactions;
    }
}
