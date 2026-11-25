package com.example.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Модель транзакції. @Data генерує гетери, сетери, toString, equals і hashCode. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String date;        // формат dd-MM-yyyy
    private double amount;      // > 0 — дохід, < 0 — витрата
    private String description;
}
