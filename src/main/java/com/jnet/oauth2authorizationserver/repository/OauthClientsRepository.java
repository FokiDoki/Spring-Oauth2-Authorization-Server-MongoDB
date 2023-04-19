package com.jnet.oauth2authorizationserver.repository;


import com.jnet.oauth2authorizationserver.entity.RegistredClientDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface OauthClientsRepository extends CrudRepository<RegistredClientDTO, String> {
    Optional<RegistredClientDTO> findByClientId(String clientId);
}
