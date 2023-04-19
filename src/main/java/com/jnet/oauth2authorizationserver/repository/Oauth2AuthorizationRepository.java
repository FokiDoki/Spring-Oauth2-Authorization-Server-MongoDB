package com.jnet.oauth2authorizationserver.repository;

import com.jnet.oauth2authorizationserver.dto.OAuth2AuthorizationDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface Oauth2AuthorizationRepository extends CrudRepository<OAuth2AuthorizationDetails, String> {
   Optional<OAuth2AuthorizationDetails> findByAccessToken_TokenValue(String tokenValue);

   Optional<OAuth2AuthorizationDetails> findByRefreshToken_TokenValue(String tokenValue);
}
