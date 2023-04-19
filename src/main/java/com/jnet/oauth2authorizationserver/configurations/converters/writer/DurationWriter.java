package com.jnet.oauth2authorizationserver.configurations.converters.writer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.Duration;
import java.util.Map;

@WritingConverter
public class DurationWriter implements Converter<Duration, Map<String,String>> {
    @Override
    public Map<String, String> convert(Duration source) {
        Map<String, String> map = Map.of(
                "amount", source.toString(),
                "_class", "java.time.Duration");
        return map;
    }
}
