package com.vFranco.vFranco.api;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.entity.ProductoEntity;
import com.vFranco.vFranco.request.CreateProductoRequest;
import com.vFranco.vFranco.service.ProductoService;



@RestController
@Controller
@RequestMapping("/Producto")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> createTipoProducto(@RequestBody CreateProductoRequest createProductoRequest){
        if(productoService.existByName(createProductoRequest.getNombre())){

            return ResponseEntity.badRequest().body("name is already taken");
          }

        return ResponseEntity.ok(productoService.creaProducto(createProductoRequest));

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ProductoEntity> update(@RequestBody ProductoEntity productoEntity, @PathVariable(value = "id") Long id) {
        ProductoEntity productoBDDEntity = productoService.get(id);
        if(productoEntity != null){
            return ResponseEntity.ok(productoService.update(productoBDDEntity, productoEntity));
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        ProductoEntity productoEntity = productoService.get(id);
        if(productoEntity != null){
            productoService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> getTipoProducto(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(productoService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductoEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "tipoproducto", required = false) Long lTipoProducto) {
        return ResponseEntity.ok(productoService.getPage(oPageable, strFilter, lTipoProducto));
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ProductoEntity> generate() {
        return ResponseEntity.ok(productoService.generate());  
    }

    @PostMapping("/generate/{amount}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") int amount) {
        return ResponseEntity.ok(productoService.generateSome(amount));
    }
} 
