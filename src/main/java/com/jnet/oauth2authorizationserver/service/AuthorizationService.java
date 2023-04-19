package com.jnet.oauth2authorizationserver.service;

import com.jnet.oauth2authorizationserver.dto.OAuth2AuthorizationDetails;
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
        authorizationRepository.save(OAuth2AuthorizationDetails.from(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        authorizationRepository.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return OAuth2AuthorizationDetails.to(
                authorizationRepository.findById(id).orElse(null)
        );
    }
    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        /*if (tokenType==OAuth2TokenType.ACCESS_TOKEN){
            return authorizationRepository.findByAccessToken_TokenValue(token);
        }*/
        Assert.notNull(tokenType, "tokenType cannot be null");
        //return authorizationRepository.findByTokenTypeAndToken(getTokenType(tokenType), token).orElse(null);
        return null;
    }
}
