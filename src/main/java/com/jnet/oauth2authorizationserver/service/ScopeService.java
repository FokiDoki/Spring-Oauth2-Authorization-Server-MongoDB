package com.jnet.oauth2authorizationserver.service;

import com.jnet.oauth2authorizationserver.entity.RoleScopes;
import com.jnet.oauth2authorizationserver.entity.Scope;
import com.jnet.oauth2authorizationserver.repository.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScopeService {
    ScopeRepository scopeRepository;

    @Autowired
    public ScopeService(ScopeRepository scopeRepository) {
        this.scopeRepository = scopeRepository;
    }

    public void addScope(String owner, String name) {
        if (!scopeRepository.existsByOwnerAndName(owner, name)){
            scopeRepository.save(new Scope(owner, name));
        }
    }

    public RoleScopes getRoleScopes(String username) {
        return new RoleScopes(username, scopeRepository.findByOwner(username));
    }


}
