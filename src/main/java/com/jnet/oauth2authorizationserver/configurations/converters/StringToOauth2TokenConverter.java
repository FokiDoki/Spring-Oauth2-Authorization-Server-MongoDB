package com.jnet.oauth2authorizationserver.configurations.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;

@ReadingConverter
public class StringToOauth2TokenConverter implements Converter<String, Class<? extends OAuth2Token>> {
    @Override
    public Class<? extends OAuth2Token> convert(String source) {
        String className = source.split(" ")[1];
        Class<? extends OAuth2Token> dat = null;
        try {
            dat = Class.forName(className).asSubclass(OAuth2AccessToken.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return dat;
    }
}
