package com.vFranco.vFranco.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.classes.LoginRequest;
import com.vFranco.vFranco.provider.JwtProvider;
import com.vFranco.vFranco.service.AuthService;



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

}
