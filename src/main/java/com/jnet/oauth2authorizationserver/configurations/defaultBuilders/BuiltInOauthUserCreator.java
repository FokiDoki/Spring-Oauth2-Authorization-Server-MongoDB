package com.jnet.oauth2authorizationserver.configurations.defaultBuilders;

import com.jnet.oauth2authorizationserver.dto.ScopeUser;
import com.jnet.oauth2authorizationserver.repository.MongoRegistredClientRepository;
import com.jnet.oauth2authorizationserver.service.ScopeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BuiltInOauthUserCreator {

    @Value("${spring.security.oauth2.superadmin.username}")
    private String superAdminUsername;
    @Value("${spring.security.oauth2.superadmin.secret}")
    private String superAdminSecret;
    @Value("${spring.security.oauth2.superadmin.enabled}")
    private Boolean superAdminEnabled;

    private MongoRegistredClientRepository mongoRegistredClientRepository;
    private ScopeService scopeService;

    @Autowired
    public BuiltInOauthUserCreator(MongoRegistredClientRepository mongoRegistredClientRepository, ScopeService scopeService) {
        this.mongoRegistredClientRepository = mongoRegistredClientRepository;
        this.scopeService = scopeService;
    }

    @PostConstruct
    public void createSuperAdmin() {
        if (mongoRegistredClientRepository.findByClientId(superAdminUsername)!=null || !superAdminEnabled) {
            return;
        }
        ScopeUser AdminScopes = scopeService.getScopeUser("ADMIN");

        RegisteredClient admin = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(superAdminUsername)
                .clientSecret(superAdminSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope(OidcScopes.OPENID)
                .scopes(AdminScopes.getScopesConsumer())
                .build();

        mongoRegistredClientRepository.save(admin);
    }
}
