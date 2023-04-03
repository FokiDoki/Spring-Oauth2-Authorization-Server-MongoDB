package com.jnet.oauth2authorizationserver.configurations.converters;

import com.mongodb.DBObject;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.net.URL;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@ReadingConverter
public class ObjectToToken implements Converter<Document, OAuth2Authorization.Token> {
    @SneakyThrows
    @Override
    public OAuth2Authorization.Token convert(Document source) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("123")
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .build();
        OAuth2Authorization.Builder oAuth2Authorization = OAuth2Authorization.withRegisteredClient(registeredClient);
        Document bsonToken = (Document) source.get("token");
        Document bsonMetadata = (Document) source.get("metadata");
        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                (String) bsonToken.get("tokenValue"),
                ((Date) bsonToken.get("issuedAt")).toInstant(),
                ((Date) bsonToken.get("expiresAt")).toInstant());

        Map<String, Object> metadataBson = Map.of(
                "metadata.token.claims", (Map<String, Object>)bsonMetadata.get("metadata-DOTtoken-DOTclaims"),
                "metadata.token.invalidated", bsonMetadata.get("metadata-DOTtoken-DOTinvalidated"));
        Map<String, Object> metadata = Collections.unmodifiableMap(metadataBson);
        Map<String, Object> claims = (Map<String, Object>) metadata.get("metadata.token.claims");
        claims.put("iss", new URL((String) claims.get("iss")));
        Consumer<Map<String, Object>> metadataConsumer = dat -> {
            dat.put("metadata.token.claims",  Collections.unmodifiableMap(claims));
            dat.put("metadata.token.invalidated", bsonMetadata.get("metadata-DOTtoken-DOTinvalidated"));
        };

        OAuth2Authorization auth = oAuth2Authorization
                .principalName("t")
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .token(oAuth2AccessToken, metadataConsumer).build();
        //builder.accessToken((OAuth2AccessToken) source);
        //OAuth2Authorization.Token token = new OAuth2Authorization.Token();
        return auth.getAccessToken();
    }
}
