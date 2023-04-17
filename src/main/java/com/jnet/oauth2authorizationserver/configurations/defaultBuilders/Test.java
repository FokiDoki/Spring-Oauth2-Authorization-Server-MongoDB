package com.jnet.oauth2authorizationserver.configurations.defaultBuilders;

import com.jnet.oauth2authorizationserver.repository.Oauth2AuthorizationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.stereotype.Component;

@Component
public class Test { //TODO: удалить
    Oauth2AuthorizationRepository authorizationRepository;
    @Autowired
    public Test(Oauth2AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }
    @PostConstruct
    public void createSuperAdmin() {
        OAuth2Authorization oAuth2Authorization = authorizationRepository.findById("9b324e9e-d0a2-47d9-b38d-b4ab064880b8").orElse(null);
        System.out.println(oAuth2Authorization);

    }
}
