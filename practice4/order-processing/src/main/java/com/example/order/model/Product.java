package com.example.order.model;

import lombok.Getter;

/** Базовий клас товару. Усі узагальнення в системі обмежені цим типом (T extends Product). */
@Getter
public abstract class Product {
    private final String id;
    private final String name;
    private final double price;

    protected Product(String id, String name, double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною: " + price);
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /** Назва типу товару для звітів. */
    public abstract String getType();

    @Override
    public String toString() {
        return String.format("%s{id=%s, name='%s', price=%.2f}", getType(), id, name, price);
    }
}
