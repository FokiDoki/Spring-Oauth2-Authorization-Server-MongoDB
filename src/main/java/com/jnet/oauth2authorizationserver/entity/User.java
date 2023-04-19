package com.jnet.oauth2authorizationserver.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
  * This class is used to store information about the user
  */
@Data
@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
@Document("oauth2_users")
public class User implements UserDetails {
    @Id
    private String id;
    private String username;
    private String password;
    private Boolean isExpired = false;
    private Boolean isLocked = false;
    private Boolean isCredentialsExpired = false;
    private Boolean isEnabled = true;
    private List<Authority> authorities;

    public User(String username, String password, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}