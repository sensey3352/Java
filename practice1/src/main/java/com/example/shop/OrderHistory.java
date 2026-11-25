package com.example.shop;

import java.util.ArrayList;
import java.util.List;

/** Самостійна робота: історія замовлень користувача. */
public class OrderHistory {
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public String toString() {
        if (orders.isEmpty()) {
            return "Історія замовлень порожня.";
        }
        StringBuilder sb = new StringBuilder("Історія замовлень:\n");
        for (Order order : orders) {
            sb.append(order).append("\n-----------------\n");
        }
        return sb.toString();
    }
}
