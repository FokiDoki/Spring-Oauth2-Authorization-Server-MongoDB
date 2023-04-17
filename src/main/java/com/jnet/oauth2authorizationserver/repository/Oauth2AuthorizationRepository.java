package com.jnet.oauth2authorizationserver.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import java.util.Optional;

public interface Oauth2AuthorizationRepository extends CrudRepository<OAuth2Authorization, String> {
    @Query("{\"tokens.class org-DOTspringframework-DOTsecurity-DOToauth2-DOTcore-DOT?0.token.tokenValue\":\"?1\"}")
    Optional<OAuth2Authorization> findByTokenTypeAndToken(String tokenType,String tokenValue);
}
