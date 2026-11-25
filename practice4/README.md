# Практична робота 4 — багатопотокова система замовлень

Maven-проект з 4 модулів Java (JPMS):

| Maven-модуль       | Java-модуль        | Що робить                                                             |
|--------------------|--------------------|-----------------------------------------------------------------------|
| order-processing   | order.processing   | Product / Electronics / Clothing (Lombok @Builder), OrderProcessor<T extends Product> |
| order-storage      | order.storage      | Потокобезпечне сховище OrderStorage<T extends Product>                |
| order-threads      | order.threads      | Пул потоків OrderThreadManager (CompletableFuture, лямбди)            |
| order-app          | order.app          | Точка входу App, генерація даних JavaFaker                            |

Пакети `...processing.internal` та `...storage.internal` не експортуються — з інших модулів вони недоступні.

Запуск (JDK 17+):

    mvn install
    mvn -pl order-app exec:java

В IntelliJ IDEA: File → Open → pom.xml (як проект), увімкнути Annotation Processing для Lombok, запустити `App`.
