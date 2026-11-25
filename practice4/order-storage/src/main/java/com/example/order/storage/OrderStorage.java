package com.example.order.storage;

import com.example.order.model.Product;
import com.example.order.processing.Order;
import com.example.order.storage.internal.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Потокобезпечне сховище замовлень. Тип T обмежений Product.
 */
public class OrderStorage<T extends Product> {

    private final Map<Long, Order<T>> orders = new ConcurrentHashMap<>();
    private final IdGenerator idGenerator = new IdGenerator();

    /** Створює та зберігає нове замовлення. */
    public Order<T> create(T product, int quantity) {
        Order<T> order = new Order<>(idGenerator.next(), product, quantity);
        orders.put(order.getId(), order);
        return order;
    }

    public Optional<Order<T>> findById(long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public List<Order<T>> findAll() {
        return new ArrayList<>(orders.values());
    }

    public List<Order<T>> findBy(Predicate<Order<T>> predicate) {
        return orders.values().stream().filter(predicate).collect(Collectors.toList());
    }

    public int size() {
        return orders.size();
    }
}
