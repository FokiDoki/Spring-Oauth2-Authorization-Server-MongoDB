package com.jnet.oauth2authorizationserver.service;

import com.jnet.oauth2authorizationserver.dto.Scope;
import com.jnet.oauth2authorizationserver.dto.ScopeUser;
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

    public ScopeUser getScopeUser(String username) {
        return new ScopeUser(username, scopeRepository.findByOwner(username));
    }


}
