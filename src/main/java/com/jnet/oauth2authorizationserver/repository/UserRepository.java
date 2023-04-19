package com.jnet.oauth2authorizationserver.repository;

import com.jnet.oauth2authorizationserver.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}
