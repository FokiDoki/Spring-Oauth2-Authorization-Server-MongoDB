package com.jnet.oauth2authorizationserver.configurations.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.util.Map;

@WritingConverter
public class SignatureAlgorithmToMap implements Converter<SignatureAlgorithm, Map<String, Object>> {
    @Override
    public Map<String,Object> convert(SignatureAlgorithm source) {
        Map<String, Object> map = Map.of(
                "name", source.getName(),
                "_class", "org.springframework.security.oauth2.jose.jws.SignatureAlgorithm");
        return map;
    }
}
