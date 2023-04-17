package com.jnet.oauth2authorizationserver.service;

import com.jnet.oauth2authorizationserver.repository.Oauth2AuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AuthorizationService implements OAuth2AuthorizationService {
    Oauth2AuthorizationRepository authorizationRepository;

    @Autowired
    public AuthorizationService(Oauth2AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }
    @Override
    public void save(OAuth2Authorization authorization) {
        authorizationRepository.save(authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        authorizationRepository.delete(authorization);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return authorizationRepository.findById(id).orElse(null);
    }

    private String getTokenType(OAuth2TokenType tokenType){
        if (tokenType.equals(OAuth2TokenType.ACCESS_TOKEN))
            return "OAuth2AccessToken";
        else if (tokenType.equals(OAuth2TokenType.REFRESH_TOKEN))
            return "OAuth2RefreshToken";
        throw new IllegalArgumentException("Unknown token type");
    }
    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.notNull(tokenType, "tokenType cannot be null");
        return authorizationRepository.findByTokenTypeAndToken(getTokenType(tokenType), token).orElse(null);
    }
}
