package com.jnet.oauth2authorizationserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
public class Authority implements GrantedAuthority{
    private Scope scope;

    @Override
    public String getAuthority() {
        return scope.getName();
    }
}
