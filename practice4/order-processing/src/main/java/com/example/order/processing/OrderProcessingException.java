package com.example.order.processing;

/** Виключення, що виникає при обробці узагальнених замовлень. */
public class OrderProcessingException extends RuntimeException {
    public OrderProcessingException(String message) {
        super(message);
    }

    public OrderProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
