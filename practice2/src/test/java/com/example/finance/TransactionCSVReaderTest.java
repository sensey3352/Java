package com.example.finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TransactionCSVReaderTest {

    // Самостійна робота: тест читання даних із CSV
    @Test
    public void testReadTransactionsFromCsv() {
        String csv = "05-12-2023,-7850,Сільпо\n" +
                     "\n" +
                     "10-12-2023,50000,Заробітна плата\n" +
                     "12-12-2023,-1200.5,Укртелеком\n";
        List<Transaction> list = TransactionCSVReader.readTransactions(new StringDataSource(csv));

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(new Transaction("05-12-2023", -7850, "Сільпо"), list.get(0)); // equals від Lombok @Data
        Assertions.assertEquals(-1200.5, list.get(2).getAmount());
    }

    @Test
    public void testReadSkipsInvalidLines() {
        String csv = "date,amount,description\n" +
                     "01-01-2024,-100,Кава\n" +
                     "broken line\n";
        List<Transaction> list = TransactionCSVReader.readTransactions(new StringDataSource(csv));
        Assertions.assertEquals(1, list.size());
    }
}
