package com.example.shop;

import java.util.ArrayList;
import java.util.List;

/** Кошик користувача. */
public class Cart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    /** Самостійна робота: видалення товару з кошика за ID. */
    public boolean removeProductById(int id) {
        return products.removeIf(p -> p.getId() == id);
    }

    /** Повертає копію списку, щоб запобігти змінам ззовні. */
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void clear() {
        products.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        if (products.isEmpty()) {
            return "Кошик порожній.";
        }
        StringBuilder sb = new StringBuilder("Кошик містить:\n");
        for (Product product : products) {
            sb.append(product).append("\n");
        }
        sb.append("Загальна вартість: ").append(getTotalPrice());
        return sb.toString();
    }
}
