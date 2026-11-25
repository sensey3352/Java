package com.example.order.processing;

import com.example.order.model.Product;
import com.example.order.processing.internal.PriceCalculator;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Узагальнений обробник замовлень. T обмежений типом Product (T extends Product),
 * тому обробник не можна створити, наприклад, для String чи Integer.
 */
public class OrderProcessor<T extends Product> {

    private final Class<T> type;
    private Consumer<Order<T>> onProcessed = o -> { };   // лямбда за замовчуванням

    public OrderProcessor(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    /** Колбек, який викликається після обробки (задається лямбдою або посиланням на метод). */
    public OrderProcessor<T> onProcessed(Consumer<Order<T>> callback) {
        this.onProcessed = callback;
        return this;
    }

    /** Обробка одного замовлення. */
    public double process(Order<T> order) {
        if (order == null) {
            throw new OrderProcessingException("Замовлення не може бути null");
        }
        order.setStatus(Order.Status.PROCESSING);
        try {
            double total = PriceCalculator.total(order.getProduct().getPrice(), order.getQuantity());
            System.out.printf("[%s] %s обробляє %s -> сума %.2f%n",
                    Thread.currentThread().getName(), type.getSimpleName(), order, total);
            order.setStatus(Order.Status.COMPLETED);
            onProcessed.accept(order);
            return total;
        } catch (RuntimeException e) {
            order.setStatus(Order.Status.FAILED);
            throw new OrderProcessingException("Помилка обробки " + order, e);
        }
    }

    /** Статичний метод — зручно використовувати як посилання на метод: OrderProcessor::processOrder */
    public static void processOrder(Order<? extends Product> order) {
        System.out.println("Processing order for: " + order.getProduct().getName());
    }

    /**
     * Безпечне приведення товару до типу обробника.
     * Якщо тип не підходить — кидаємо OrderProcessingException замість ClassCastException.
     */
    public T cast(Product product) {
        if (!type.isInstance(product)) {
            throw new OrderProcessingException("Обробник " + type.getSimpleName()
                    + " не може обробити товар типу " + product.getClass().getSimpleName());
        }
        return type.cast(product);
    }

    /** Відбір із загального списку лише товарів потрібного типу (лямбда-фільтр). */
    public List<T> filter(List<? extends Product> products) {
        return products.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    /** Узагальнений статичний метод з обмеженням: сума цін будь-якого списку товарів. */
    public static <P extends Product> double totalPrice(List<P> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    /** Узагальнений фільтр з предикатом. */
    public static <P extends Product> List<P> filterBy(List<P> products, Predicate<? super P> predicate) {
        return products.stream().filter(predicate).collect(Collectors.toList());
    }
}
