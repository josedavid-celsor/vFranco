/* package com.vFranco.vFranco.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.entity.ProductoEntity;
import com.vFranco.vFranco.helper.ValidationHelper;
import com.vFranco.vFranco.repository.ProductoRepository;
import com.vFranco.vFranco.request.CreateProductoRequest;
import com.vFranco.vFranco.service.ProductoService;



@RestController
@Controller
@RequestMapping("/Producto")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;
    
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

    public Page<ProductoEntity> getPage(Pageable oPageable, String strFilter) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<ProductoEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = productoRepository.findAll(oPageable);
        } else {
            oPage = productoRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }
} 
 */