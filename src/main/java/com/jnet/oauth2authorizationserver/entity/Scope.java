package com.jnet.oauth2authorizationserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("oauth2_scopes")
public class Scope {
    private String owner;
    private String name;

    @Override
    public String toString() {
        return owner + "." + name;
    }
}
