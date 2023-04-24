package com.jnet.oauth2authorizationserver.service;

import com.jnet.oauth2authorizationserver.entity.RegistredClientDTO;
import com.jnet.oauth2authorizationserver.repository.OauthClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistredClientsService implements RegisteredClientRepository {
    OauthClientsRepository registredClientRepository;
    @Autowired
    public RegistredClientsService(OauthClientsRepository registredClientRepository) {
        this.registredClientRepository = registredClientRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        registredClientRepository.save(new RegistredClientDTO(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        return registredClientRepository.findById(id).orElse(new RegistredClientDTO()).getRegistredClient();

    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return registredClientRepository.findByClientId(clientId).orElse(new RegistredClientDTO()).getRegistredClient();
    }
}
