package com.example.order.processing.internal;

/**
 * Внутрішній клас модуля order.processing.
 * Пакет не експортується в module-info.java, тому з інших модулів він недоступний.
 */
public final class PriceCalculator {
    private static final double TAX = 0.20; // ПДВ 20%

    private PriceCalculator() { }

    public static double total(double price, int quantity) {
        return Math.round(price * quantity * (1 + TAX) * 100.0) / 100.0;
    }
}
