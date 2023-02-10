package com.vFranco.vFranco.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.request.LoginRequest;
import com.vFranco.vFranco.request.RecoverRequest;
import com.vFranco.vFranco.request.RegisterRequest;
import com.vFranco.vFranco.entity.AuthoritysEntity;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.helper.RandomHelper;
import com.vFranco.vFranco.provider.JwtProvider;
import com.vFranco.vFranco.repository.UsuarioRepository;

import java.util.Collections;
import java.util.List;


import javax.mail.MessagingException;

@Service
public class AuthService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  
  @Autowired
  private EmailService emailService;

  org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
  /* BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
 */
  private JwtProvider jwtProvider;

  public AuthService(UsuarioRepository usuarioRepository, JwtProvider jwtProvider) {
    this.usuarioRepository = usuarioRepository;
    this.jwtProvider = jwtProvider;
  }

  public UsuarioEntity login(LoginRequest loginRequest) {
    UsuarioEntity usuario = usuarioRepository.findByUsername(loginRequest.getUsername());
    System.out.print("EJECUTO ENTRADA METODO LOGIN");
    System.out.println("------------------------------------------");
    // Para encriptar el password
    String encryptedPassword=DigestUtils.md5Hex(loginRequest.getPassword());
    if (usuario == null) {
      throw new RuntimeException("User not found");
    }
    if (!usuario.getPassword().equals(encryptedPassword)) {
      throw new RuntimeException("Password incorrect");
    }
    System.out.println(usuario.getVerificationCode().length());
    if(usuario.getVerificationCode() != null || !usuario.getVerificationCode().isEmpty() ||  !usuario.getVerificationCode().trim().isEmpty()){
      throw new RuntimeException("You have to verify you account first, check your email");
    }
    UserDetails userDetails = loadUserByUsername(loginRequest.getUsername());
    String token = jwtProvider.generateJwt(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
    encryptedPassword, userDetails.getAuthorities()));
    usuario.setToken(token);
    return usuario;
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

  public UsuarioEntity register(RegisterRequest registerRequest, String verification_code) {

    UsuarioEntity user = new UsuarioEntity();
    AuthoritysEntity authority = new AuthoritysEntity();

    // Para encriptar el password
    String encryptedPassword=DigestUtils.md5Hex(registerRequest.getPassword());
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
    user.setVerificationCode(verification_code);
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

  public boolean verifyEmail(String verification_code){
    UsuarioEntity usuario = usuarioRepository.findByVerificationCode(verification_code);
    if(usuario != null){
      usuario.setVerificationCode(null);
      usuarioRepository.save(usuario);
      return true;
    }else{
      throw new RuntimeException("Email incorrecto");
    }
  }

  public boolean verifyRecover(String passwordCode){
    UsuarioEntity usuario = usuarioRepository.findByNewPasswordCode(passwordCode);
    return usuario != null;
  }

  public String recover(RecoverRequest recoverRequest){
    UsuarioEntity usuario = usuarioRepository.findByNewPasswordCode(recoverRequest.getRecoverCode());
    if(usuario != null){
      if(recoverRequest.getPassword1().equals(recoverRequest.getPassword2())){
        String encryptedPassword=DigestUtils.md5Hex(recoverRequest.getPassword1());
        usuario.setPassword(encryptedPassword);
        usuario.setNewPasswordCode(null);
        usuarioRepository.save(usuario);
        return "";
      }else{
        throw new RuntimeException("Las contraseñas deben ser iguales");
      }
    }else{
      throw new RuntimeException("El código de recuperación es incorrecto");
    }
  }

  public boolean iniciarRecover(String usuario){
    UsuarioEntity usuarioEntity = usuarioRepository.findByUsername(usuario);
    if(usuario != null){
      String code =  RandomHelper.getToken(10);
      try {
        emailService.sendCode(usuarioEntity.getEmail(), code);
        usuarioEntity.setNewPasswordCode(code);
        usuarioRepository.save(usuarioEntity);
        return true;
      } catch (MessagingException e) {
        // TODO Auto-generated catch block
        throw new RuntimeException("Ha habido un problema en el servidor intentelo más tarde");
      }
    }else{
      throw new RuntimeException("No se ha encontrado el usuario a recuperar");
    }
  }
}
