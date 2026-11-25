/** Модуль керування потоками обробки замовлень. */
module order.threads {
    requires transitive order.storage;      // транзитивно дає доступ і до order.processing

    exports com.example.order.threads;
}
