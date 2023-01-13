package com.vFranco.vFranco.api;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.entity.FacturaEntity;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.provider.JwtProvider;
import com.vFranco.vFranco.service.FacturaService;
import com.vFranco.vFranco.service.UsuarioService;

@RestController
@Controller
@RequestMapping("/Factura")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UsuarioService usuarioService;
    //Con esto dos metodos que hemos creado, si el usuario conectado es admin, debera recuperar todas las facturas de la aplicación pero si no es admin, solo las suyas
    @GetMapping("")
    @PreAuthorize("hasAuthority('cliente') or hasAuthority('admin')")
    public ResponseEntity<Page<FacturaEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 5, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter){
                if(jwtProvider.isAdminConnected()){
                    return new ResponseEntity<Page<FacturaEntity>>(facturaService.getPage(oPageable, strFilter), HttpStatus.OK);
                }else{
                    String username = jwtProvider.getUsernameConnected();
                    UsuarioEntity usuario = this.usuarioService.findbyUsername(username);
                    return new ResponseEntity<Page<FacturaEntity>>(facturaService.getPageByUsuario(oPageable, strFilter, usuario), HttpStatus.OK);
                }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        FacturaEntity facturaEntity = facturaService.get(id);
        if(facturaEntity != null){
            facturaService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }
}
