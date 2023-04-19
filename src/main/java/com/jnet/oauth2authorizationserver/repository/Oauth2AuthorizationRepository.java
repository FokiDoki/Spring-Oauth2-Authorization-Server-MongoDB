package com.jnet.oauth2authorizationserver.repository;

import com.jnet.oauth2authorizationserver.entity.OAuth2AuthorizationDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface Oauth2AuthorizationRepository extends CrudRepository<OAuth2AuthorizationDTO, String> {
   Optional<OAuth2AuthorizationDTO> findByAccessToken_TokenValue(String tokenValue);

   Optional<OAuth2AuthorizationDTO> findByRefreshToken_TokenValue(String tokenValue);
}
