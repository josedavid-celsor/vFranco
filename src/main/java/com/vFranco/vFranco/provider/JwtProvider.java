package com.vFranco.vFranco.provider;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.vFranco.vFranco.repository.UsuarioRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtProvider {

  @Autowired
  UsuarioRepository usuarioRepository;

  String secretkey;
  Long validms;
  
  public JwtProvider(@Value("${jwt.secret}") String secretkey, @Value("${jwt.valid}") Long validms) {
    this.secretkey = secretkey;
    this.validms = validms;
  }

  public String generateJwt(Authentication authentication) {

    String username = authentication.getName();
    
    GrantedAuthority authority = authentication.getAuthorities().iterator().next();
    String role = authority.getAuthority();

    Claims claims = Jwts.claims().setSubject(username);
    claims.put("role", role);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validms * 60);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretkey)
        .compact();
  }

  public Authentication getAuthentication(String jwt) {

    Claims claims = Jwts.parser()
        .setSigningKey(secretkey)
        .parseClaimsJws(jwt)
        .getBody();

    String username = claims.getSubject();
    String role = (String) claims.get("role");
    GrantedAuthority authority = new SimpleGrantedAuthority(role);
    List<GrantedAuthority> authorities = Collections.singletonList(authority);

    return new UsernamePasswordAuthenticationToken(username, null, authorities);
  }

  public boolean validatejwt(String jwt) {
    try {
      Jwts.parser().setSigningKey(secretkey).parseClaimsJws(jwt);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      /* logger.error("Expired or invalid JWT token"); */
    }
    return false;
  }

  //Recibe un token lo procesa y devuelve si el rol es de admin
  public boolean validatejwtAdmin(String jwtAdmin) {
    
    if(!validatejwt(jwtAdmin)){
      return false;
    }
    Authentication user = getAuthentication(jwtAdmin);
    GrantedAuthority authority = user.getAuthorities().iterator().next();
    String role = authority.getAuthority();
    
    return "admin".equals(role);
  }

  //Devuelve el username del usuario conectado
  public String getUsernameConnected() {    
    return (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  //En el caso de que el usuario este conectado es un admin devuleve true, si no es false
  public boolean isAdminConnected(){
    Authentication authenticationUser = SecurityContextHolder.getContext().getAuthentication();
    GrantedAuthority authority = authenticationUser.getAuthorities().iterator().next();
    String role = authority.getAuthority();
    
    return "admin".equals(role);
  }
}
