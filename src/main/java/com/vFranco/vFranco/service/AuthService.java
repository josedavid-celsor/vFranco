package com.vFranco.vFranco.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.vFranco.vFranco.classes.LoginRequest;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.exception.UnauthorizedException;
import com.vFranco.vFranco.provider.JwtProvider;
import com.vFranco.vFranco.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private HttpServletRequest oRequest;


    
    @Autowired
    private  UsuarioRepository usuarioRepository;

    
    private JwtProvider jwtProvider;

    public AuthService(UsuarioRepository usuarioRepository, JwtProvider jwtProvider) {
    this.usuarioRepository = usuarioRepository;
    this.jwtProvider = jwtProvider;
    }

    public String login(LoginRequest loginRequest) {
    UsuarioEntity usuario = usuarioRepository.findByUsername(loginRequest.getUsername());

    if (usuario == null) {
      throw new RuntimeException("Usuario no encontrado");
    }

    if (!usuario.getPassword().equals(loginRequest.getPassword())) {
      throw new RuntimeException("Contraseña incorrecta");
    }

    return jwtProvider.generateJwt(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
  }

     
}
 