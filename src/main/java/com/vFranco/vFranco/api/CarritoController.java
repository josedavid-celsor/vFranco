package com.vFranco.vFranco.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.entity.CarritoEntity;
import com.vFranco.vFranco.service.CarritoService;

@RestController
@Controller
@RequestMapping("/Carrito")
public class CarritoController {
     
    @Autowired
    private CarritoService carritoService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('cliente') or hasAuthority('admin')")
    public ResponseEntity<List<CarritoEntity>> getCarrito(){
        //Esto se usa para el username
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       
        return ResponseEntity.ok(carritoService.getCarrito(username));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('cliente') or hasAuthority('admin')")
    public ResponseEntity<Void> deleteOne(@PathVariable(value = "id") Long id) {
        
        CarritoEntity carritoEntity = carritoService.getOne(id);
        if(carritoEntity != null){
            carritoService.deleteOneProduct(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('cliente') or hasAuthority('admin')")
    public ResponseEntity<Void> deleteAll() {
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        carritoService.deleteCarrito(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/compra/{id}")
    @PreAuthorize("hasAuthority('cliente') or hasAuthority('admin')")
    public ResponseEntity<Void> compra(@PathVariable(value = "id") Long id) {
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        carritoService.buyCarrito(username, id);
        return ResponseEntity.ok().build();  
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('cliente') or hasAuthority('admin')")
    public ResponseEntity<CarritoEntity> insert(@PathVariable(value = "id") Long id) {
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  ResponseEntity.ok(carritoService.insert(username, id));  
    }
}
