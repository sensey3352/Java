package com.example.finance;

import java.io.IOException;
import java.io.Reader;

/**
 * Задача 1.2*: джерело даних. Читання (звідки) відокремлене від обробки (як парсити),
 * тому можна додати нове джерело без зміни логіки обробки.
 */
public interface DataSource {
    Reader open() throws IOException;
}
