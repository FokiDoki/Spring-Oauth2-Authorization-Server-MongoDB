package com.jnet.oauth2authorizationserver.configurations.converters.writer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.Instant;

@WritingConverter
public class InstantWriter implements Converter<Instant, String> {

    @Override
    public String convert(Instant source) {
        return source.toString();
    }
}
