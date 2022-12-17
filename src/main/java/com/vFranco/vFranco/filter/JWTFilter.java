package com.vFranco.vFranco.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vFranco.vFranco.securityConfig.JWTConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vFranco.vFranco.provider.JwtProvider;


//Intercepta las peticiones y busca la presencia de un token, en el acso de que no este no deja seguir la aplicación
@Component
public class JWTFilter extends OncePerRequestFilter {


    @Autowired
    private JwtProvider jwtProvider;
  
    public JWTFilter(JwtProvider jwtProvider) {
      this.jwtProvider = jwtProvider;
    }
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /* System.out.println("XDDD"); */
        String jwt = resolveToken(request);
        if (!"null".equals(jwt)) {
            // If a JWT token was found, try to authenticate the user using the token
            Authentication authentication = jwtProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
       /*  System.out.println("XDD222D"); */
        // Continue with the request
        filterChain.doFilter(request, response);

    }

    private String resolveToken(HttpServletRequest request) {
        // Check the Authorization header for a JWT token
        String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


  }