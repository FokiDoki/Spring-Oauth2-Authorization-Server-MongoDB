package com.jnet.oauth2authorizationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Scope {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
