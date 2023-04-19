package com.jnet.oauth2authorizationserver.configurations.converters.reader;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Instant;

@ReadingConverter
public class InstantReader implements Converter<String, Instant> {

    @Override
    public Instant convert(String source) {
        return Instant.parse(source);
    }
}
