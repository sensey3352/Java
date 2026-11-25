package com.example.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    private Category electronics;
    private Product laptop;
    private Product phone;

    @BeforeEach
    void setUp() {
        electronics = new Category(1, "Електроніка");
        laptop = new Product(1, "Ноутбук", 20000, "опис", electronics);
        phone = new Product(2, "Смартфон", 10000, "опис", new Category(2, "Смартфони"));
    }

    @Test
    void cartTotalAndRemove() {
        Cart cart = new Cart();
        cart.addProduct(laptop);
        cart.addProduct(phone);
        assertEquals(30000, cart.getTotalPrice());

        assertTrue(cart.removeProductById(2));
        assertFalse(cart.removeProductById(99));
        assertEquals(1, cart.getProducts().size());
    }

    @Test
    void orderCopiesCartAndHistoryKeepsIt() {
        Cart cart = new Cart();
        cart.addProduct(laptop);
        Order order = new Order(cart);
        cart.clear();

        assertEquals(1, order.getProducts().size());
        assertEquals("Нове", order.getStatus());

        OrderHistory history = new OrderHistory();
        history.addOrder(order);
        assertEquals(1, history.getOrders().size());
    }

    @Test
    void searchByNameAndCategory() {
        Catalog catalog = new Catalog();
        catalog.addProduct(laptop);
        catalog.addProduct(phone);

        assertEquals(1, catalog.searchByName("ноут").size());
        assertEquals(1, catalog.searchByCategory("смарт").size());
        assertTrue(catalog.searchByName("xyz").isEmpty());
    }

    @Test
    void lombokGettersAndSetters() {
        laptop.setPrice(15000);
        assertEquals(15000, laptop.getPrice());
        assertEquals("Електроніка", laptop.getCategory().getName());
    }
}
