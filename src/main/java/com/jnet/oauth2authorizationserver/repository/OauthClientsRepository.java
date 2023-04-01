package com.jnet.oauth2authorizationserver.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;

public interface OauthClientsRepository extends CrudRepository<RegisteredClient, String> {
    Optional<RegisteredClient> findByClientId(String clientId);
}
