package com.example.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Каталог товарів з пошуком (самостійна робота). */
public class Catalog {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public Product findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    /** Пошук за назвою (без урахування регістру, часткове співпадіння). */
    public List<Product> searchByName(String query) {
        String q = query.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    /** Пошук за назвою категорії. */
    public List<Product> searchByCategory(String categoryName) {
        String q = categoryName.toLowerCase();
        return products.stream()
                .filter(p -> p.getCategory().getName().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }
}
