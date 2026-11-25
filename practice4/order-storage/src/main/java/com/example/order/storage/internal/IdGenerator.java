package com.example.order.storage.internal;

import java.util.concurrent.atomic.AtomicLong;

/** Внутрішній генератор ID (не експортується з модуля order.storage). */
public class IdGenerator {
    private static final AtomicLong COUNTER = new AtomicLong(1);

    public long next() {
        return COUNTER.getAndIncrement();
    }
}
