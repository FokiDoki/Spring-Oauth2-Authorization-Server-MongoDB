package com.jnet.oauth2authorizationserver.scopes;

import com.jnet.oauth2authorizationserver.dto.Scope;

import java.util.Set;
import java.util.function.Consumer;

public class ADMIN {
    public final static Scope READ = new Scope("ADMIN.READ");
    public final static Scope WRITE = new Scope("ADMIN.WRITE");
    public final static Scope DELETE = new Scope("ADMIN.DELETE");

    public final static Consumer<Set<String>> SCOPES = scopes -> {
        scopes.add(READ.getName());
        scopes.add(WRITE.getName());
        scopes.add(DELETE.getName());
    };
}
