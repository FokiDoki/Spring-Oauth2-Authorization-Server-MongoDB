package com.jnet.oauth2authorizationserver.configurations.defaultBuilders;

import com.jnet.oauth2authorizationserver.service.ScopeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component("DefaultScopesBuilder")
public class DefaultScopesBuilder {
    private ScopeService scopeService;

    @Autowired
    public DefaultScopesBuilder(ScopeService scopeService) {
        this.scopeService = scopeService;
    }

    @PostConstruct
    public void buildAdminScopes() {
        scopeService.addScope("ADMIN", "READ");
        scopeService.addScope("ADMIN", "WRITE");
        scopeService.addScope("ADMIN", "DELETE");
    }

}
