package com.example.finance;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Джерело — локальний файл. */
@RequiredArgsConstructor
public class FileDataSource implements DataSource {
    private final String path;

    @Override
    public Reader open() throws IOException {
        return Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
    }
}
