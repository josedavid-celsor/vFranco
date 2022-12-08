package com.vFranco.vFranco.securityConfig;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vFranco.vFranco.filter.JWTFilter;
import com.vFranco.vFranco.provider.JwtProvider;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    
    private final JwtProvider jwtProvider;
  
    public JWTConfigurer(JwtProvider jwtProvider) {
      this.jwtProvider = jwtProvider;
    }
  
    @Override
    public void configure(HttpSecurity http) throws Exception {
      JWTFilter customFilter = new JWTFilter(jwtProvider);
      http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
  }