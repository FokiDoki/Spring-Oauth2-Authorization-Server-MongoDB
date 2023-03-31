package com.jnet.oauth2authorizationserver.configurations;

import com.jnet.oauth2authorizationserver.configurations.converters.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConverters {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {

        return new MongoCustomConversions(
                Arrays.asList(
                        new DurationToMapConverter(),
                        new MapToSignatureAlgorithmConveter(),
                        new ObjToDurationConverter(),
                        new SignatureAlgorithmToMap()
                ));
    }
}
