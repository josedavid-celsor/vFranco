package com.vFranco.vFranco.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.entity.TipoProductoEntity;
import com.vFranco.vFranco.request.CreateTPRequest;
import com.vFranco.vFranco.service.TipoProductoService;

@RestController
@Controller
@RequestMapping("/TipoProducto")
public class TipoProductoController {
    
    @Autowired
    private TipoProductoService tipoProductoService;

    @PostMapping("/")
    public ResponseEntity<?> createTipoProducto(@RequestBody CreateTPRequest createRequest){
        if(tipoProductoService.existByName(createRequest.getNombre())){

            return ResponseEntity.badRequest().body("name is already taken");
          }

        return ResponseEntity.ok(tipoProductoService.creaTipoProducto(createRequest));

    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoEntity> getTipoProducto(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(tipoProductoService.get(id));
    }
}
