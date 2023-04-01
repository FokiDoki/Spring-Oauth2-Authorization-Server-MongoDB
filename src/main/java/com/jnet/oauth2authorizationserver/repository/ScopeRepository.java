package com.jnet.oauth2authorizationserver.repository;

import com.jnet.oauth2authorizationserver.dto.Scope;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ScopeRepository extends CrudRepository<Scope, String> {
    Scope findByName(String name);

    Set<Scope> findByOwner(String owner);

    Boolean existsByOwnerAndName(String owner, String name);
}
