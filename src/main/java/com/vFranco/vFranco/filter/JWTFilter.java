package com.vFranco.vFranco.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vFranco.vFranco.classes.LoginRequest;
import com.vFranco.vFranco.provider.JwtProvider;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;
  
    public JWTFilter(JwtProvider jwtProvider) {
      this.jwtProvider = jwtProvider;
    }
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        LoginRequest loginRequest = new ObjectMapper().readValue(requestBody, LoginRequest.class);

        Authentication authentication = authenticationManager.authenticate
        
        (new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jsonWebToken =jwtProvider.generateJwt(authentication);
  
      if (jsonWebToken != null && jwtProvider.validatejwt(jsonWebToken)) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
  
      filterChain.doFilter(request, response);
    }


  }