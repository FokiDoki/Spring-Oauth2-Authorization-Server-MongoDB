package com.jnet.oauth2authorizationserver.configurations.converters.reader;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

@ReadingConverter
public class OAuth2AccessTokenReader implements Converter<Map<String, Object>, OAuth2AccessToken> {
    @Override
    public OAuth2AccessToken convert(Map<String, Object> source) {
        return new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                (String) source.get("tokenValue"),
                Instant.parse((String) source.get("issuedAt")),
                Instant.parse((String) source.get("expiresAt")),
                new HashSet<>( (ArrayList<String>) source.get("scopes") ));
    }
}
