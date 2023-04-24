package com.jnet.oauth2authorizationserver.configurations.converters.reader;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Duration;
import java.util.Map;

@ReadingConverter
public class DurationReader implements Converter<Map<String, String>, Duration> {
    @Override
    public Duration convert(Map<String, String> source) {
        return Duration.parse(source.get("amount"));
    }
}

