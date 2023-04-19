package com.jnet.oauth2authorizationserver.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "oauth2_authorizations")
public class OAuth2AuthorizationDTO {
    @Id
    private String id;
    private String registeredClientId;
    private String principalName;
    private AuthorizationGrantType authorizationGrantType;
    private Set<String> authorizedScopes;
    private OAuth2AccessToken accessToken;
    private Map<String, Object> accessTokenMetadata;
    private OAuth2RefreshToken refreshToken;
    private Map<String, Object> refreshTokenMetadata;
    private Map<String, Object> attributes;

    public static OAuth2AuthorizationDTO from(OAuth2Authorization token){
        OAuth2AuthorizationDTO details = new OAuth2AuthorizationDTO();
        details.id = token.getId();
        details.registeredClientId = token.getRegisteredClientId();
        details.principalName = token.getPrincipalName();
        details.authorizationGrantType = token.getAuthorizationGrantType();
        details.authorizedScopes = token.getAuthorizedScopes();
        details.accessToken = token.getAccessToken().getToken();
        details.accessTokenMetadata = token.getAccessToken().getMetadata();
        if (token.getRefreshToken()!=null){
            details.refreshToken = token.getRefreshToken().getToken();
            details.refreshTokenMetadata = token.getRefreshToken().getMetadata();
        }
        details.attributes = token.getAttributes();
        return details;
    }
    private static RegisteredClient getRegistredClientFromOauth2Authorization(OAuth2AuthorizationDTO details){
        Consumer<Set<String>> scopesConsumer = scopes -> scopes=details.authorizedScopes;
        return RegisteredClient.withId(details.registeredClientId)
                .clientId(details.principalName)
                .scopes(scopesConsumer)
                .authorizationGrantType(details.authorizationGrantType)
                .build();
    }
    public static OAuth2Authorization to(OAuth2AuthorizationDTO details){
        if (details==null){
            return null;
        }
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(
                getRegistredClientFromOauth2Authorization(details))
                .id(details.id)
                .principalName(details.principalName)
                .authorizationGrantType(details.authorizationGrantType)
                .authorizedScopes(details.authorizedScopes)
                .attributes(dat -> dat=details.attributes);
        if (details.refreshToken != null)
            builder.token(details.refreshToken, dat -> dat.putAll(details.refreshTokenMetadata));
        builder.token(details.accessToken, dat ->  dat.putAll(details.accessTokenMetadata));
        return builder.build();
    }
}
