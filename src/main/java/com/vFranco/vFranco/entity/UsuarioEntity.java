package com.vFranco.vFranco.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UsuarioEntity {
    
    Long id;
    String username;
    String password;
    private Collection<? extends GrantedAuthority> authoritys;

    public UsuarioEntity(Long id, String username, String password, Collection<? extends GrantedAuthority> authoritys) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authoritys = authoritys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(Collection<? extends GrantedAuthority> authoritys) {
        this.authoritys = authoritys;
    }

   
    
}
