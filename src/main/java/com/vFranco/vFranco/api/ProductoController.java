package com.vFranco.vFranco.api;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<?> createTipoProducto(@RequestBody CreateProductoRequest createProductoRequest){
        if(productoService.existByName(createProductoRequest.getNombre())){

            return ResponseEntity.badRequest().body("name is already taken");
          }

        return ResponseEntity.ok(productoService.creaProducto(createProductoRequest));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> getTipoProducto(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(productoService.get(id));
    }

    // /producto?page=0&size=10&sort=precio,desc&filter=verde&tipoproducto=2
    @GetMapping("")
    public ResponseEntity<Page<ProductoEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "tipoproducto", required = false) Long lTipoProducto) {
        return new ResponseEntity<Page<ProductoEntity>>(productoService.getPage(oPageable, strFilter, lTipoProducto), HttpStatus.OK);
    }
} 
