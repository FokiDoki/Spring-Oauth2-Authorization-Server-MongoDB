package com.jnet.oauth2authorizationserver.service;

import com.jnet.oauth2authorizationserver.repository.Oauth2AuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;

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
        OAuth2Authorization dbAuth = authorizationRepository.findById(authorization.getId()).orElse(null);
        System.out.println(dbAuth.hashCode());
        System.out.println(authorization.hashCode());
        System.out.println(authorization.equals(dbAuth));
        System.out.println("aga");
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        authorizationRepository.delete(authorization);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return authorizationRepository.findById(id).orElse(null);//todo: создать конвертер для OAuth2Authorization
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        //return    authorizationRepository.findById(token);
        return null;
    }
}
