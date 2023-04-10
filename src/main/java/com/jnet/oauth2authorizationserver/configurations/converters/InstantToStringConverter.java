package com.jnet.oauth2authorizationserver.configurations.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.time.Instant;

@WritingConverter
public class InstantToStringConverter implements Converter<Instant, String> {

    @Override
    public String convert(Instant source) {
        return source.toString();
    }
}
