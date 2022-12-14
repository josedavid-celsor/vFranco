package com.vFranco.vFranco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.request.LoginRequest;
import com.vFranco.vFranco.request.RegisterRequest;
import com.vFranco.vFranco.entity.AuthoritysEntity;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.provider.JwtProvider;
import com.vFranco.vFranco.repository.UsuarioRepository;

import java.util.Collections;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
  /* BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
 */
  private JwtProvider jwtProvider;

  public AuthService(UsuarioRepository usuarioRepository, JwtProvider jwtProvider) {
    this.usuarioRepository = usuarioRepository;
    this.jwtProvider = jwtProvider;
  }

  public String login(LoginRequest loginRequest) {
    UsuarioEntity usuario = usuarioRepository.findByUsername(loginRequest.getUsername());

    // Para encriptar el password
    String encryptedPassword = passwordEncoder.encode(loginRequest.getPassword());
    if (usuario == null) {
      throw new RuntimeException("Usuario no encontrado");
    }
    System.out.println(usuario.getPassword());
    System.out.println(encryptedPassword);
    if (!usuario.getPassword().equals(encryptedPassword)) {
      throw new RuntimeException("Contraseña incorrecta");
    }
    UserDetails userDetails = loadUserByUsername(loginRequest.getUsername());
    return jwtProvider.generateJwt(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
        encryptedPassword, userDetails.getAuthorities()));
  }

  // A partir de un usuario saca el usuario, para ver si existe y en el caso de
  // que exista devuelve los detalles del usuario
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Look up the user with the given username in the database
    UsuarioEntity usuario = usuarioRepository.findByUsername(username);

    // If the user was not found, throw a UsernameNotFoundException
    if (usuario == null) {
      throw new UsernameNotFoundException(username);
    }
    GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getAuthority().getNombre());
    List<GrantedAuthority> authorities = Collections.singletonList((GrantedAuthority) authority);
    // Return a Spring Security User object populated with the user's information
    return new User(usuario.getUsername(), usuario.getPassword(), authorities);
  }

  public UsuarioEntity register(RegisterRequest registerRequest) {

    UsuarioEntity user = new UsuarioEntity();
    AuthoritysEntity authority = new AuthoritysEntity();

    // Para encriptar el password
    String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());
    authority.setId(2L);
    authority.setNombre("cliente");
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setNombre(registerRequest.getNombre());
    user.setApellido(registerRequest.getApellido());
    user.setApellido2(registerRequest.getApellido2());
    user.setDni(registerRequest.getDni());
    user.setPassword(encryptedPassword);
    user.setAuthority(authority);

    return usuarioRepository.save(user);
  }

  public boolean existByUsername(String username) {
    try {
      return usuarioRepository.existsByUsername(username);
    } catch (Exception e) {
      return false;
    }

  }

  public boolean existByEmail(String email) {
    try {
      return usuarioRepository.existsByEmail(email);
    } catch (Exception e) {
      return false;
    }
  }
}
