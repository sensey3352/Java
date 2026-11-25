package com.example.finance;

/** Обробник одного рядка даних — не залежить від джерела. */
public interface TransactionParser {
    Transaction parse(String line);
}
