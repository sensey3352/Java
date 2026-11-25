package com.example.finance;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/** Джерело — URL. */
@RequiredArgsConstructor
public class UrlDataSource implements DataSource {
    private final String url;

    @Override
    public Reader open() throws IOException {
        return new InputStreamReader(URI.create(url).toURL().openStream(), StandardCharsets.UTF_8);
    }
}
