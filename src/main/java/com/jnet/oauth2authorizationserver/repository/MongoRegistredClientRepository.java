package com.jnet.oauth2authorizationserver.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

@Component

public class MongoRegistredClientRepository implements RegisteredClientRepository {
    OauthClientsRepository registredClientRepository;
    @Autowired
    public MongoRegistredClientRepository(OauthClientsRepository registredClientRepository) {
        this.registredClientRepository = registredClientRepository;
        registredClientRepository.findByClientId("12");
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        registredClientRepository.save(registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        return registredClientRepository.findById(id).orElse(null);

    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return registredClientRepository.findByClientId(clientId).orElse(null);
    }
}
