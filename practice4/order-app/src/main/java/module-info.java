/** Модуль-застосунок: точка входу. */
module order.app {
    requires order.threads;    // транзитивно: order.storage, order.processing
    requires javafaker;        // автоматичний модуль (JavaFaker не має module-info)
}
