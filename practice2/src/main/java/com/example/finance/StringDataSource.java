package com.example.finance;

import lombok.RequiredArgsConstructor;

import java.io.Reader;
import java.io.StringReader;

/** Джерело — рядок у пам'яті (зручно для тестів). */
@RequiredArgsConstructor
public class StringDataSource implements DataSource {
    private final String content;

    @Override
    public Reader open() {
        return new StringReader(content);
    }
}
