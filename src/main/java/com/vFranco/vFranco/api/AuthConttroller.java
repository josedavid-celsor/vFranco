package com.vFranco.vFranco.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.classes.LoginRequest;
import com.vFranco.vFranco.provider.JwtProvider;


@RestController
@RequestMapping("/Auth")
public class AuthConttroller {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;
    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate
                                            (new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jsonWebToken =jwtProvider.generateJwt(authentication);
    return ResponseEntity.ok(jsonWebToken);
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestParam("token") String token){
        boolean isValid =jwtProvider.validatejwt(token);
        return ResponseEntity.ok(isValid);
    }

}
