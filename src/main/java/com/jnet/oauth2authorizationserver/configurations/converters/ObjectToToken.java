package com.jnet.oauth2authorizationserver.configurations.converters;

import com.mongodb.DBObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

@ReadingConverter
@SuppressWarnings("unchecked")
@Component
public class ObjectToToken implements Converter<Document, OAuth2Authorization.Token> {

    @Value("${spring.data.mongodb.dot.replace}")
    private String mongoDotReplacement;

    @SneakyThrows
    @Override
    public OAuth2Authorization.Token convert(Document source) {

        RegisteredClient registeredClient = getEmptyRegistredClient();
        OAuth2Authorization.Builder oAuth2Authorization = OAuth2Authorization.withRegisteredClient(registeredClient);
        TokenData token = TokenData.fromMap((Document) source.get("token"));
        TokenMetadata tokenMetadata = TokenMetadata.fromMap( replaceMongoDots((Document) source.get("metadata") ));

        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                token.getTokenValue(),
                token.getIssuedAt(),
                token.getExpiresAt(),
                token.getScopes());

        Consumer<Map<String, Object>> metadataConsumer = dat -> {
            dat.put("metadata.token.claims",  Collections.unmodifiableMap(tokenMetadata.toMap()));
            dat.put("metadata.token.invalidated", tokenMetadata.getInvalidated());
        };

        OAuth2Authorization auth = oAuth2Authorization
                .principalName("null")
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .token(oAuth2AccessToken, metadataConsumer).build();
        return auth.getAccessToken();
    }

    private RegisteredClient getEmptyRegistredClient(){
        return RegisteredClient.withId(UUID.randomUUID().toString()).clientId("null")
                .scope("null")
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .build();
    }

    private Map<String, Object> replaceMongoDots(Map<String, Object> obj){
        Map<String, Object> replacedObj = new HashMap<>();
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            if (entry.getValue() instanceof Map) {
                replaceMongoDots((Map<String, Object>) entry.getValue());
            }
            if (entry.getKey().contains(mongoDotReplacement)){
                String newKey = entry.getKey().replace(mongoDotReplacement, ".");
                replacedObj.put(newKey, entry.getValue());
            }
        }
        return replacedObj;
    }


    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class TokenData{
        private String tokenValue;
        private Instant issuedAt;
        private Instant expiresAt;
        private HashSet<String> scopes;

        static TokenData fromMap(Map<String, Object> tokenData){
            Assert.notEmpty(tokenData, "tokenData cannot be empty");
            return new TokenData(
                    (String) tokenData.get("tokenValue"),
                    Instant.parse((String) tokenData.get("issuedAt")),
                    Instant.parse((String) tokenData.get("expiresAt")),
                    new HashSet<>( (ArrayList<String>) tokenData.get("scopes") )
            );
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    private static class TokenMetadata{
        private String sub;
        private List<String> aud;
        private Instant nbf;
        private LinkedHashSet<String> scope;
        private URL iss;
        private Instant exp;
        private Instant iat;
        private Boolean invalidated;



        Map<String, Object> toMap(){
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", sub);
            claims.put("aud", aud);
            claims.put("nbf", nbf);
            claims.put("scope", scope);
            claims.put("iss", iss);
            claims.put("exp", exp);
            claims.put("iat", iat);
            return claims;
        }


        static TokenMetadata fromMap(Map<String, Object> metadata) throws MalformedURLException {
            Assert.notEmpty(metadata, "metadata cannot be empty");

            Map<String, Object> claims = (Map<String, Object>) metadata.get("metadata.token.claims");
            Boolean isInvalidated = (Boolean) metadata.get("metadata.token.invalidated");
            return new TokenMetadata(
                    String.valueOf(claims.get("sub")),
                    Collections.singletonList(((ArrayList<String>)claims.get("aud")).get(0)),
                    Instant.parse( String.valueOf(claims.get("nbf")) ),
                    new LinkedHashSet<String>((ArrayList<String>)claims.get("scope")),
                    new URL(String.valueOf(claims.get("iss"))),
                    Instant.parse( String.valueOf(claims.get("exp")) ),
                    Instant.parse( String.valueOf(claims.get("iat")) ),
                    isInvalidated
            );
        }
    }



}
