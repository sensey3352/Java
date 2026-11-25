package com.example.shop;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Створення категорій
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        // Каталог товарів
        Catalog catalog = new Catalog();
        catalog.addProduct(new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics));
        catalog.addProduct(new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном та високою автономністю", smartphones));
        catalog.addProduct(new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories));
        catalog.addProduct(new Product(4, "Планшет", 9999.00, "Планшет з діагоналлю 11 дюймів", electronics));
        catalog.addProduct(new Product(5, "Чохол", 399.00, "Силіконовий чохол для смартфона", accessories));

        Cart cart = new Cart();
        OrderHistory history = new OrderHistory();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВиберіть опцію:");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Зробити замовлення");
            System.out.println("5 - Видалити товар з кошика");
            System.out.println("6 - Історія замовлень");
            System.out.println("7 - Пошук товару за назвою");
            System.out.println("8 - Пошук товару за категорією");
            System.out.println("0 - Вийти");

            if (!scanner.hasNextLine()) return;
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
                continue;
            }

            switch (choice) {
                case 1:
                    printList(catalog.getProducts());
                    break;
                case 2: {
                    System.out.println("Введіть ID товару для додавання до кошика:");
                    Product p = catalog.findById(readInt(scanner));
                    if (p != null) {
                        cart.addProduct(p);
                        System.out.println("Додано: " + p.getName());
                    } else {
                        System.out.println("Товар з таким ID не знайдено");
                    }
                    break;
                }
                case 3:
                    System.out.println(cart);
                    break;
                case 4:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
                    } else {
                        Order order = new Order(cart);
                        history.addOrder(order);
                        System.out.println("Замовлення оформлено:");
                        System.out.println(order);
                        cart.clear();
                    }
                    break;
                case 5:
                    System.out.println("Введіть ID товару для видалення з кошика:");
                    if (cart.removeProductById(readInt(scanner))) {
                        System.out.println("Товар видалено з кошика.");
                    } else {
                        System.out.println("Такого товару в кошику немає.");
                    }
                    break;
                case 6:
                    System.out.println(history);
                    break;
                case 7:
                    System.out.println("Введіть назву (або її частину):");
                    printList(catalog.searchByName(scanner.nextLine().trim()));
                    break;
                case 8:
                    System.out.println("Введіть назву категорії:");
                    printList(catalog.searchByCategory(scanner.nextLine().trim()));
                    break;
                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;
                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
            }
        }
    }

    private static void printList(List<Product> list) {
        if (list.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Некоректне число, спробуйте ще раз:");
            }
        }
    }
}
