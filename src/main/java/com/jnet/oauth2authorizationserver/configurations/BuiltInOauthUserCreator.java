package com.jnet.oauth2authorizationserver.configurations;

import com.jnet.oauth2authorizationserver.repository.MongoRegistredClientRepository;
import com.jnet.oauth2authorizationserver.scopes.ADMIN;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Component
public class BuiltInOauthUserCreator {

    @Value("${spring.security.oauth2.superadmin.username}")
    private String superAdminUsername;
    @Value("${spring.security.oauth2.superadmin.secret}")
    private String superAdminSecret;
    @Value("${spring.security.oauth2.superadmin.enabled}")
    private Boolean superAdminEnabled;

    private MongoRegistredClientRepository mongoRegistredClientRepository;

    @Autowired
    public BuiltInOauthUserCreator(MongoRegistredClientRepository mongoRegistredClientRepository) {
        this.mongoRegistredClientRepository = mongoRegistredClientRepository;
    }

    @PostConstruct
    public void createSuperAdmin() {
        if (mongoRegistredClientRepository.findByClientId(superAdminUsername)!=null || !superAdminEnabled) {
            return;
        }
        RegisteredClient admin = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(superAdminUsername)
                .clientSecret(superAdminSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope(OidcScopes.OPENID)
                .scopes(ADMIN.SCOPES)
                .build();

        mongoRegistredClientRepository.save(admin);
    }
}
