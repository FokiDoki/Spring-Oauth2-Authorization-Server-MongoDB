package com.jnet.oauth2authorizationserver.service;

import com.jnet.oauth2authorizationserver.entity.OAuth2AuthorizationDTO;
import com.jnet.oauth2authorizationserver.repository.Oauth2AuthorizationRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;

@Service
public class AuthorizationService implements OAuth2AuthorizationService {
    Oauth2AuthorizationRepository authorizationRepository;
    @Autowired
    public AuthorizationService(Oauth2AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }
    @Override
    public void save(OAuth2Authorization authorization) {
        authorizationRepository.save(OAuth2AuthorizationDTO.from(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        authorizationRepository.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return OAuth2AuthorizationDTO.to(
                authorizationRepository.findById(id).orElse(null)
        );
    }
    @SneakyThrows
    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        if (tokenType==OAuth2TokenType.ACCESS_TOKEN){
            return OAuth2AuthorizationDTO.to(
                    authorizationRepository.findByAccessToken_TokenValue(token).orElse(null)
            );
        } else if (tokenType==OAuth2TokenType.REFRESH_TOKEN){
            return OAuth2AuthorizationDTO.to(
                    authorizationRepository.findByRefreshToken_TokenValue(token).orElse(null)
            );
        }
        throw new InvalidAttributeValueException("Invalid tokenType, "+tokenType.getValue());
    }
}
