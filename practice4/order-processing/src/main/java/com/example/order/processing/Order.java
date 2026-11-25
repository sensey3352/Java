package com.example.order.processing;

import com.example.order.model.Product;

import java.util.Objects;

/**
 * Узагальнене замовлення. Тип T обмежений Product або його підтипами.
 */
public class Order<T extends Product> {

    public enum Status { NEW, PROCESSING, COMPLETED, FAILED }

    private final long id;
    private final T product;
    private final int quantity;
    private volatile Status status = Status.NEW;

    public Order(long id, T product, int quantity) {
        this.id = id;
        this.product = Objects.requireNonNull(product, "Товар не може бути null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути > 0");
        }
        this.quantity = quantity;
    }

    public long getId() { return id; }
    public T getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return "Order#" + id + " " + product.getName() + " x" + quantity + " [" + status + "]";
    }
}
