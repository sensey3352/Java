/**
 * Модуль обробки замовлень: модель товарів та узагальнений обробник замовлень.
 * Пакет com.example.order.processing.internal НЕ експортується —
 * інші модулі не мають до нього доступу (контроль доступу на рівні модулів).
 */
module order.processing {
    requires java.base;
    requires static lombok;           // Lombok потрібен лише під час компіляції

    exports com.example.order.model;
    exports com.example.order.processing;
}
