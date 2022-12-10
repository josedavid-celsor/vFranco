package com.vFranco.vFranco.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.classes.LoginRequest;
import com.vFranco.vFranco.classes.RegisterRequest;
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

    @Autowired
    private JwtProvider jwtProvider;
    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody LoginRequest loginRequest){
        /* System.out.println("XDDD"); */
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestParam("token") String token){
        boolean isValid =jwtProvider.validatejwt(token);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        if(authService.existByUsername(registerRequest.getUsername())){

            return ResponseEntity.badRequest().body("username is already taken");
          }
        if(authService.existByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body("email is already taken");
          }
        UsuarioEntity usuarioEntity = authService.register(registerRequest);
        String token = jwtProvider.generateJwt(new UsernamePasswordAuthenticationToken(usuarioEntity.getUsername(), usuarioEntity.getPassword()));

        return ResponseEntity.ok(token);
    }

}
