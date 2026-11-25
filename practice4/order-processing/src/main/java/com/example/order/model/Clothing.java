package com.example.order.model;

import lombok.Builder;
import lombok.Getter;

/** Одяг. Об'єкти створюються через патерн Builder (Lombok @Builder). */
@Getter
public class Clothing extends Product {
    private final String size;
    private final String color;
    private final String material;

    @Builder
    public Clothing(String id, String name, double price, String size, String color, String material) {
        super(id, name, price);
        this.size = size;
        this.color = color;
        this.material = material;
    }

    @Override
    public String getType() {
        return "Clothing";
    }

    @Override
    public String toString() {
        return super.toString() + "[size=" + size + ", color=" + color + ", material=" + material + "]";
    }
}
