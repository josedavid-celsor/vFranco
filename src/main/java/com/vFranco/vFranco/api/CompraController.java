package com.vFranco.vFranco.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vFranco.vFranco.entity.CompraEntity;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.provider.JwtProvider;
import com.vFranco.vFranco.service.CompraService;
import com.vFranco.vFranco.service.UsuarioService;

@RestController
@Controller
@RequestMapping("/Compra")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtProvider jwtProvider;


    @GetMapping("/{id}")
    public ResponseEntity<List<CompraEntity>> getByFactura(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(compraService.getByFactura(id));
    } 

   
    @GetMapping("/getAll")
    public ResponseEntity<List<CompraEntity>> getAllByUsuario(){
        if(jwtProvider.isAdminConnected()){
            return ResponseEntity.ok(compraService.getAll());
        }else{
            String username = jwtProvider.getUsernameConnected();
            return ResponseEntity.ok(compraService.getAllByUsuario(username));
        }
    }
}
