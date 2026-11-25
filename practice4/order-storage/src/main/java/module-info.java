/** Модуль зберігання замовлень. */
module order.storage {
    requires transitive order.processing;   // модулі, що використовують storage, бачать і Product/Order

    exports com.example.order.storage;      // пакет internal не експортується
}
