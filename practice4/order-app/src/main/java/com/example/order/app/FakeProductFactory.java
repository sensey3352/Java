package com.example.order.app;

import com.example.order.model.Clothing;
import com.example.order.model.Electronics;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Наповнення об'єктів випадковими даними за допомогою JavaFaker + Lombok Builder. */
class FakeProductFactory {

    private static final String[] SIZES = {"XS", "S", "M", "L", "XL"};
    private final Faker faker = new Faker();

    Electronics electronics() {
        return Electronics.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .name(faker.commerce().productName())
                .price(faker.number().randomDouble(2, 1000, 50000))
                .brand(faker.company().name())
                .warrantyMonths(faker.options().option(6, 12, 24, 36))
                .build();
    }

    Clothing clothing() {
        return Clothing.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .name(faker.commerce().productName())
                .price(faker.number().randomDouble(2, 200, 5000))
                .size(faker.options().option(SIZES))
                .color(faker.color().name())
                .material(faker.commerce().material())
                .build();
    }

    List<Electronics> electronicsList(int n) {
        return IntStream.range(0, n).mapToObj(i -> electronics()).collect(Collectors.toList());
    }

    List<Clothing> clothingList(int n) {
        return IntStream.range(0, n).mapToObj(i -> clothing()).collect(Collectors.toList());
    }

    int quantity() {
        return faker.number().numberBetween(1, 5);
    }
}
