package com.jnet.oauth2authorizationserver.configurations.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.Duration;
import java.util.Map;

@WritingConverter
public class DurationToMapConverter implements Converter<Duration, Map<String,Object>> {
    @Override
    public Map<String,Object> convert(Duration source) {
        Map<String, Object> map = Map.of(
                "ISO8601", source.toString(),
                "_class", "java.time.Duration");
        return map;
    }
}
