package com.vFranco.vFranco.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.server.ResponseStatusException;


import com.vFranco.vFranco.classes.LoginRequest;
import com.vFranco.vFranco.classes.RegisterRequest;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.exception.UnauthorizedException;
import com.vFranco.vFranco.provider.JwtProvider;
import com.vFranco.vFranco.repository.UsuarioRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

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

    //A partir de un usuario saca el usuario, para ver si existe y en el caso de que exista devuelve los detalles del usuario 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Look up the user with the given username in the database
        UsuarioEntity usuario = usuarioRepository.findByUsername(username);

        // If the user was not found, throw a UsernameNotFoundException
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = Collections.singletonList((GrantedAuthority) usuario.getAuthority());
        // Return a Spring Security User object populated with the user's information
        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }

    public UsuarioEntity register(RegisterRequest registerRequest){
      
      UsuarioEntity user = new UsuarioEntity();
      user.setUsername(registerRequest.getUsername());
      user.setEmail(registerRequest.getEmail());
      user.setNombre(registerRequest.getNombre());
      user.setApellido(registerRequest.getApellido());
      user.setApellido2(registerRequest.getApellido2());
      user.setDni(registerRequest.getDni());
      user.setPassword(registerRequest.getPassword());

      return usuarioRepository.save(user);
    }

    public boolean existByUsername(String username){
      return usuarioRepository.existsByUsername(username);
    }

    public boolean existByEmail(String email){
      return usuarioRepository.existsByEmail(email);
    }
}
 