package com.example.order.app;

import com.example.order.model.Clothing;
import com.example.order.model.Electronics;
import com.example.order.model.Product;
import com.example.order.processing.Order;
import com.example.order.processing.OrderProcessingException;
import com.example.order.processing.OrderProcessor;
import com.example.order.storage.OrderStorage;
import com.example.order.threads.OrderThreadManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// import com.example.order.processing.internal.PriceCalculator;
// ^ Не скомпілюється: пакет не експортується модулем order.processing (контроль доступу).

public class App {

    public static void main(String[] args) {
        FakeProductFactory factory = new FakeProductFactory();

        // 1. Генерація товарів (JavaFaker + Lombok @Builder)
        List<Electronics> electronics = factory.electronicsList(4);
        List<Clothing> clothing = factory.clothingList(4);

        // 2. Сховища для різних типів (узагальнення)
        OrderStorage<Electronics> electronicsStorage = new OrderStorage<>();
        OrderStorage<Clothing> clothingStorage = new OrderStorage<>();
        electronics.forEach(p -> electronicsStorage.create(p, factory.quantity()));   // лямбда
        clothing.forEach(p -> clothingStorage.create(p, factory.quantity()));

        System.out.println("=== Створені замовлення ===");
        electronicsStorage.findAll().forEach(System.out::println);   // посилання на метод
        clothingStorage.findAll().forEach(System.out::println);

        // 3. Посилання на статичний метод обробки
        System.out.println("\n=== OrderProcessor::processOrder ===");
        List<Order<? extends Product>> all = new ArrayList<>();
        all.addAll(electronicsStorage.findAll());
        all.addAll(clothingStorage.findAll());
        all.forEach(OrderProcessor::processOrder);

        // 4. Фільтрація змішаного списку за типом (лямбди + Class<T>)
        List<Product> mixed = new ArrayList<>();
        mixed.addAll(electronics);
        mixed.addAll(clothing);
        OrderProcessor<Electronics> electronicsProcessor = new OrderProcessor<>(Electronics.class)
                .onProcessed(o -> System.out.println("   ✓ готово: " + o));
        OrderProcessor<Clothing> clothingProcessor = new OrderProcessor<>(Clothing.class)
                .onProcessed(App::logClothing);                       // посилання на метод
        System.out.println("\nЕлектроніки у змішаному списку: " + electronicsProcessor.filter(mixed).size());
        System.out.println("Одягу у змішаному списку: " + clothingProcessor.filter(mixed).size());
        System.out.printf("Сума цін усіх товарів: %.2f%n", OrderProcessor.totalPrice(mixed));
        System.out.println("Дорогі товари (> 10000): "
                + OrderProcessor.filterBy(mixed, p -> p.getPrice() > 10000).size());

        // 5. Обробка виключень при роботі з узагальненими типами
        System.out.println("\n=== Обробка виключень ===");
        try {
            electronicsProcessor.cast(clothing.get(0));   // одяг у процесор електроніки
        } catch (OrderProcessingException e) {
            System.out.println("Очікувана помилка: " + e.getMessage());
        }
        try {
            electronicsProcessor.process(null);
        } catch (OrderProcessingException e) {
            System.out.println("Очікувана помилка: " + e.getMessage());
        }

        // 6. Багатопотокова обробка
        System.out.println("\n=== Багатопотокова обробка ===");
        try (OrderThreadManager manager = new OrderThreadManager(3)) {
            manager.runAsync(() -> System.out.println("Старт обробки в потоці " + Thread.currentThread().getName()));

            List<CompletableFuture<Double>> futures = new ArrayList<>();
            futures.addAll(manager.submitAll(electronicsStorage.findAll(), electronicsProcessor));
            futures.addAll(manager.submitAll(clothingStorage.findAll(), clothingProcessor));
            manager.awaitAll(futures);

            System.out.printf("%nЗагальний дохід: %.2f, помилок: %d%n", manager.getRevenue(), manager.getFailedCount());
        }

        long completed = electronicsStorage.findBy(o -> o.getStatus() == Order.Status.COMPLETED).size()
                + clothingStorage.findBy(o -> o.getStatus() == Order.Status.COMPLETED).size();
        System.out.println("Виконано замовлень: " + completed);
    }

    private static void logClothing(Order<Clothing> order) {
        System.out.println("   ✓ одяг " + order.getProduct().getSize() + ": " + order);
    }
}
