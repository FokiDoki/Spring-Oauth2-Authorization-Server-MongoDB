package com.jnet.oauth2authorizationserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.function.Consumer;

@Data
@AllArgsConstructor
public class RoleScopes {
    private String username;
    private Set<Scope> scopes;


    public Consumer<Set<String>> getScopesConsumer() {
        return scopesCons -> {
            scopes.forEach(scope -> scopesCons.add(scope.toString()));
        };
    }


 }
