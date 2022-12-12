package com.vFranco.vFranco.api;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.request.LoginRequest;
import com.vFranco.vFranco.request.RegisterRequest;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.provider.JwtProvider;
import com.vFranco.vFranco.service.AuthService;


//Define los end points de la aplicación
@RestController
@Controller
@RequestMapping("/Auth")
public class AuthConttroller {

    @Autowired
    private AuthService authService;

    private static final Logger logger = Logger.getLogger(AuthConttroller.class.getName());

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody LoginRequest loginRequest){
        /* System.out.println("XDDD"); */
       try{
        return ResponseEntity.ok(authService.login(loginRequest));
       } catch(Exception e){
        logger.warning(e.toString());
        return ResponseEntity.badRequest().body("username doesn`t exist");
       }
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestParam("token") String token){
        boolean isValid =jwtProvider.validatejwt(token);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        try{
            if(authService.existByUsername(registerRequest.getUsername())){

                return ResponseEntity.badRequest().body("username is already taken");
              }
            if(authService.existByEmail(registerRequest.getEmail())){
                return ResponseEntity.badRequest().body("email is already taken");
              }
            UsuarioEntity usuarioEntity = authService.register(registerRequest);
            GrantedAuthority authority = new SimpleGrantedAuthority(usuarioEntity.getAuthority().getNombre());
            List<GrantedAuthority> authorities = Collections.singletonList((GrantedAuthority) authority);
            String token = jwtProvider.generateJwt(new UsernamePasswordAuthenticationToken(usuarioEntity.getUsername(), usuarioEntity.getPassword(), authorities));
    
            return ResponseEntity.ok(token);
        }catch(Exception e){
            logger.warning(e.toString());
            return ResponseEntity.ok("Por que eres así?");
        }
       
    }

}
