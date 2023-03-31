package com.jnet.oauth2authorizationserver.configurations.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.util.Map;

@ReadingConverter
public class MapToSignatureAlgorithmConveter implements Converter<Map<String, String>, SignatureAlgorithm> {
    @Override
    public SignatureAlgorithm convert(Map<String, String> source) {
        return SignatureAlgorithm.from(source.get("name"));
    }
}
