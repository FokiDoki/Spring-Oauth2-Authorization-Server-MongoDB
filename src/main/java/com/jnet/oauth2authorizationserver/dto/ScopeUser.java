package com.jnet.oauth2authorizationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.function.Consumer;

@Data
@AllArgsConstructor
public class ScopeUser {
    private String username;
    private Set<Scope> scopes;


    public Consumer<Set<String>> getScopesConsumer() {
        return scopesCons -> {
            scopes.forEach(scope -> scopesCons.add(scope.toString()));
        };
    }


 }
