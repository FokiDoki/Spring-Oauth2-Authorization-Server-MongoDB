package com.jnet.oauth2authorizationserver.scopes;

import java.util.Set;
import java.util.function.Consumer;

public class ADMIN {
    public final static String READ = "ADMIN.READ";
    public final static String WRITE = "ADMIN.WRITE";
    public final static String DELETE = "ADMIN.DELETE";

    public final static Consumer<Set<String>> SCOPES = scopes -> {
        scopes.add(READ);
        scopes.add(WRITE);
        scopes.add(DELETE);
    };
}
