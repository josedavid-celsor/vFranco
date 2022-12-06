package com.vFranco.vFranco.provider;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.vFranco.vFranco.entity.UsuarioEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtProvider {
    String secretkey;
    Long validms;

    public JwtProvider(@Value("${jwt.secret}") String secretkey, @Value("${jwt.secret}") Long validms) {
        this.secretkey = secretkey;
        this.validms = validms;
    }
    
    public String generateJwt(Authentication authentication){
        UsuarioEntity userPrincipal = (UsuarioEntity) authentication.getPrincipal();
        Date now = new Date();
        Date maxValiDate = new Date(now.getTime()+validms);
        return Jwts.builder()
        .setSubject(userPrincipal.getId().toString())
        .setIssuedAt(now)
        .setExpiration(maxValiDate)
        .signWith(SignatureAlgorithm.HS256, this.secretkey)
        .compact();
    }

    public Authentication getAuthentication(String jwt){

    Claims claims = Jwts.parser()
      .setSigningKey(secretkey)
      .parseClaimsJws(jwt)
      .getBody();

    Long id = Long.parseLong(claims.getSubject());
    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("roles", String.class));
    UsuarioEntity userPrincipal = new UsuarioEntity(id, null, null, authorities);
    return new UsernamePasswordAuthenticationToken(userPrincipal, "", authorities);
    }

    public boolean validatejwt(String jwt){
     try {
        Jwts.parser().setSigningKey(secretkey).parseClaimsJws(jwt);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      /*  logger.error("Expired or invalid JWT token"); */ 
    }
    return false;
    }
}
