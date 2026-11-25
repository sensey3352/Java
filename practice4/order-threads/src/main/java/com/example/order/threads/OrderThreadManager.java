package com.example.order.threads;

import com.example.order.model.Product;
import com.example.order.processing.Order;
import com.example.order.processing.OrderProcessingException;
import com.example.order.processing.OrderProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

/**
 * Керує пулом потоків, у якому паралельно обробляються замовлення.
 */
public class OrderThreadManager implements AutoCloseable {

    private final ExecutorService executor;
    private final DoubleAdder revenue = new DoubleAdder();
    private final AtomicInteger failed = new AtomicInteger();

    public OrderThreadManager(int threads) {
        AtomicInteger n = new AtomicInteger(1);
        // лямбда як ThreadFactory
        this.executor = Executors.newFixedThreadPool(threads, r -> new Thread(r, "order-worker-" + n.getAndIncrement()));
    }

    /** Відправляє всі замовлення в пул; кожне обробляється в окремій задачі. */
    public <T extends Product> List<CompletableFuture<Double>> submitAll(List<Order<T>> orders,
                                                                         OrderProcessor<T> processor) {
        List<CompletableFuture<Double>> futures = new ArrayList<>();
        for (Order<T> order : orders) {
            CompletableFuture<Double> f = CompletableFuture
                    .supplyAsync(() -> processor.process(order), executor)   // лямбда
                    .whenComplete((sum, ex) -> {
                        if (ex == null) {
                            revenue.add(sum);
                        } else {
                            failed.incrementAndGet();
                            System.err.println("Помилка: " + ex.getCause().getMessage());
                        }
                    });
            futures.add(f);
        }
        return futures;
    }

    /** Простий запуск задачі в окремому потоці (Runnable як лямбда). */
    public void runAsync(Runnable task) {
        executor.submit(task);
    }

    public void awaitAll(List<CompletableFuture<Double>> futures) {
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            if (!(e.getCause() instanceof OrderProcessingException)) {
                throw e;
            }
            // помилки окремих замовлень уже залоговано в whenComplete
        }
    }

    public double getRevenue() {
        return revenue.sum();
    }

    public int getFailedCount() {
        return failed.get();
    }

    @Override
    public void close() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
