package com.example.order.model;

import lombok.Builder;
import lombok.Getter;

/** Електроніка. Об'єкти створюються через патерн Builder (Lombok @Builder). */
@Getter
public class Electronics extends Product {
    private final String brand;
    private final int warrantyMonths;

    @Builder
    public Electronics(String id, String name, double price, String brand, int warrantyMonths) {
        super(id, name, price);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getType() {
        return "Electronics";
    }

    @Override
    public String toString() {
        return super.toString() + "[brand=" + brand + ", warranty=" + warrantyMonths + " міс.]";
    }
}
