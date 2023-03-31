package com.jnet.oauth2authorizationserver.configurations.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Duration;
import java.util.Map;

@ReadingConverter
public class ObjToDurationConverter implements Converter<Map<String, String>,Duration> {
    @Override
    public Duration convert(Map<String, String> source) {
        return Duration.parse(source.get("ISO8601"));
    }
}

