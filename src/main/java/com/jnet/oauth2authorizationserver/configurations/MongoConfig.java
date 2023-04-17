package com.jnet.oauth2authorizationserver.configurations;

import com.jnet.oauth2authorizationserver.configurations.converters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Arrays;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.dot.replace}")
    private String dotReplace;
    ObjectToToken objectToToken;

    @Autowired
    public MongoConfig(ObjectToToken objectToToken) {
        this.objectToToken = objectToToken;
    }
    @Bean
    public MappingMongoConverter mongoConverter(MongoDatabaseFactory mongoFactory, MongoMappingContext mongoMappingContext) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoFactory);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mongoConverter.setMapKeyDotReplacement(dotReplace);
        mongoConverter.setCustomConversions(mongoCustomConversions());
        return mongoConverter;
    }

    public MongoCustomConversions mongoCustomConversions(){
        return new MongoCustomConversions(
                Arrays.asList(
                        new DurationToMapConverter(),
                        new MapToSignatureAlgorithmConverter(),
                        new ObjToDurationConverter(),
                        new SignatureAlgorithmToMap(),
                        new StringToOauth2TokenConverter(),
                        objectToToken,
                        new StringToInstantConverter(),
                        new InstantToStringConverter()
                ));
    }

}
