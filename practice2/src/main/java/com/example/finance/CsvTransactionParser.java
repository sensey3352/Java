package com.example.finance;

/** Парсер рядка CSV: дата,сума,опис */
public class CsvTransactionParser implements TransactionParser {
    @Override
    public Transaction parse(String line) {
        String[] values = line.split(",", 3);
        if (values.length < 3) {
            throw new IllegalArgumentException("Некоректний рядок CSV: " + line);
        }
        return new Transaction(values[0].trim(), Double.parseDouble(values[1].trim()), values[2].trim());
    }
}
