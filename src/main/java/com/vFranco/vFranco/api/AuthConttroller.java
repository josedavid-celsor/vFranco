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
import com.vFranco.vFranco.request.RecoverRequest;
import com.vFranco.vFranco.request.RegisterRequest;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.helper.RandomHelper;
import com.vFranco.vFranco.provider.JwtProvider;
import com.vFranco.vFranco.service.AuthService;
import com.vFranco.vFranco.service.EmailService;


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

    @Autowired
    private EmailService emailService;

    @PostMapping("/token")
    public ResponseEntity<UsuarioEntity> generateToken(@RequestBody LoginRequest loginRequest){
       
        return ResponseEntity.ok(authService.login(loginRequest));
      
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestParam("token") String token){
        boolean isValid =jwtProvider.validatejwt(token);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/verifyadmin")
    public ResponseEntity<Boolean> verifyAdmin(@RequestParam("tokenadmin") String tokenadmin){
        boolean isValid =jwtProvider.validatejwtAdmin(tokenadmin);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        try{
            if(authService.existByUsername(registerRequest.getUsername())){

                return ResponseEntity.badRequest().body("username is already taken");
              }
            if(authService.existByEmail(registerRequest.getEmail())){
                return ResponseEntity.badRequest().body("email is already taken");
              }
            String verification_code = RandomHelper.getToken(10);
            UsuarioEntity usuarioEntity = authService.register(registerRequest, verification_code);
            GrantedAuthority authority = new SimpleGrantedAuthority(usuarioEntity.getAuthority().getNombre());
            List<GrantedAuthority> authorities = Collections.singletonList((GrantedAuthority) authority);
            String token = jwtProvider.generateJwt(new UsernamePasswordAuthenticationToken(usuarioEntity.getUsername(), usuarioEntity.getPassword(), authorities));
            usuarioEntity.setToken(token);
            // Enviar correo electrónico de confirmación
                emailService.sendHtmlEmail(
                usuarioEntity.getEmail(), 
                usuarioEntity.getVerificationCode()
               );
            return ResponseEntity.ok(usuarioEntity);
            
        }catch(Exception e){
            logger.warning(e.toString());
            return ResponseEntity.ok("Por que eres así?");
        }
       
    }

    @GetMapping("/verifyMail")
    public ResponseEntity<Boolean> verifyMail(@RequestParam("verification_code") String verification_code){
        return ResponseEntity.ok(authService.verifyEmail(verification_code));
    }

    @GetMapping("/verifyRecover")
    public ResponseEntity<Boolean> verifyRecover(@RequestParam("passworCode") String passworCode){
        return ResponseEntity.ok(authService.verifyRecover(passworCode));
    }

    @GetMapping("/iniciarRecover")
    public ResponseEntity<Boolean> iniciarRecover(@RequestParam("usuario") String usuario){
        return ResponseEntity.ok(authService.iniciarRecover(usuario));
    }

    @PostMapping("/recover")
    public ResponseEntity<String> recover(@RequestBody RecoverRequest recoverRequest){   
        return ResponseEntity.ok(authService.recover(recoverRequest));
    }
}
