package com.example.shop;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/** Замовлення, сформоване з товарів у кошику. */
@Getter
public class Order {
    private static int counter = 1;

    private final int id;
    private final List<Product> products;   // Список товарів у замовленні
    private final double totalPrice;        // Загальна вартість замовлення
    @Setter
    private String status;                  // Статус замовлення

    public Order(Cart cart) {
        this.id = counter++;
        this.products = new ArrayList<>(cart.getProducts());
        this.totalPrice = cart.getTotalPrice();
        this.status = "Нове";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Замовлення №" + id + ":\n");
        for (Product product : products) {
            sb.append(product).append("\n");
        }
        sb.append("Загальна вартість: ").append(totalPrice).append("\n");
        sb.append("Статус: ").append(status);
        return sb.toString();
    }
}
