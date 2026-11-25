package com.example.finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

class TransactionReportGeneratorTest {

    @Test
    public void testStars() {
        Assertions.assertEquals("***", TransactionReportGenerator.stars(3000));
        Assertions.assertEquals("*", TransactionReportGenerator.stars(200));
        Assertions.assertEquals("", TransactionReportGenerator.stars(0));
        Assertions.assertEquals("*****", TransactionReportGenerator.stars(500, 100));
    }

    // Пункт 4 завдання: класи абстрактні, екземпляри не створюються
    @Test
    public void testClassesAreAbstract() {
        Assertions.assertTrue(Modifier.isAbstract(TransactionCSVReader.class.getModifiers()));
        Assertions.assertTrue(Modifier.isAbstract(TransactionAnalyzer.class.getModifiers()));
        Assertions.assertTrue(Modifier.isAbstract(TransactionReportGenerator.class.getModifiers()));
    }
}
