package com.jnet.oauth2authorizationserver.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Document("oauth2_clients")
public class RegistredClientDTO {
    private String id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods = new HashSet<>();
    private Set<AuthorizationGrantType> authorizationGrantTypes = new HashSet<>();
    private Set<String> redirectUris = new HashSet<>();
    private Set<String> scopes = new HashSet<>();
    private ClientSettings clientSettings;
    private TokenSettings tokenSettings;
    public RegistredClientDTO(RegisteredClient registeredClient){
        this.id = registeredClient.getId();
        this.clientId = registeredClient.getClientId();
        this.clientIdIssuedAt = registeredClient.getClientIdIssuedAt();
        this.clientSecret = registeredClient.getClientSecret();
        this.clientSecretExpiresAt = registeredClient.getClientSecretExpiresAt();
        this.clientName = registeredClient.getClientName();
        if (!CollectionUtils.isEmpty(registeredClient.getClientAuthenticationMethods())) {
            this.clientAuthenticationMethods.addAll(registeredClient.getClientAuthenticationMethods());
        }
        if (!CollectionUtils.isEmpty(registeredClient.getAuthorizationGrantTypes())) {
            this.authorizationGrantTypes.addAll(registeredClient.getAuthorizationGrantTypes());
        }
        if (!CollectionUtils.isEmpty(registeredClient.getRedirectUris())) {
            this.redirectUris.addAll(registeredClient.getRedirectUris());
        }
        if (!CollectionUtils.isEmpty(registeredClient.getScopes())) {
            this.scopes.addAll(registeredClient.getScopes());
        }
        this.clientSettings = ClientSettings.withSettings(registeredClient.getClientSettings().getSettings()).build();
        this.tokenSettings = TokenSettings.withSettings(registeredClient.getTokenSettings().getSettings()).build();
    }

    public RegistredClientDTO() {

    }

    public RegisteredClient getRegistredClient(){
        if (id==null){
            return null;
        }
        return RegisteredClient
                .withId(this.id)
                .clientId(this.clientId)
                .clientIdIssuedAt(this.clientIdIssuedAt)
                .clientSecret(this.clientSecret)
                .clientSecretExpiresAt(this.clientSecretExpiresAt)
                .clientName(this.clientName)
                .clientAuthenticationMethods(m -> m.addAll(this.clientAuthenticationMethods))
                .authorizationGrantTypes(m -> m.addAll(this.authorizationGrantTypes))
                .redirectUris(m -> m.addAll(this.redirectUris))
                .scopes(m -> m.addAll(this.scopes))
                .clientSettings(this.clientSettings)
                .tokenSettings(this.tokenSettings)
                .build();
    }

}
