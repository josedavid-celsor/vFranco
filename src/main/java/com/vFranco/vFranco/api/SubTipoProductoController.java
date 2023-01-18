package com.vFranco.vFranco.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.entity.SubTipoProductoEntity;
import com.vFranco.vFranco.request.CreateSTPRequest;
import com.vFranco.vFranco.service.SubTipoProductoService;

@RestController
@Controller
@RequestMapping("/Subtipo")
public class SubTipoProductoController {
    @Autowired
    private SubTipoProductoService subTipoProductoService;



   @PostMapping("/")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> createTipoProducto(@RequestBody CreateSTPRequest createRequest){

        if(subTipoProductoService.existByName(createRequest.getNombre())){
            return ResponseEntity.badRequest().body("name is already taken");
          }
        return ResponseEntity.ok(subTipoProductoService.creaSubTipoProducto(createRequest));

    } 

    @GetMapping("/{id}")
    public ResponseEntity<SubTipoProductoEntity> getSubTipoProducto(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(subTipoProductoService.get(id));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Page<SubTipoProductoEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 5, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<Page<SubTipoProductoEntity>>(subTipoProductoService.getPage(oPageable, strFilter), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        SubTipoProductoEntity subTipoProductoEntity = subTipoProductoService.get(id);
        if(subTipoProductoEntity != null){
            subTipoProductoService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<SubTipoProductoEntity> update(@RequestBody SubTipoProductoEntity subTipoProductoEntity, @PathVariable(value = "id") Long id) {
        SubTipoProductoEntity subTipoProductoBDDEntity = subTipoProductoService.get(id);
        if(subTipoProductoEntity != null){
            return ResponseEntity.ok(subTipoProductoService.update(subTipoProductoBDDEntity, subTipoProductoEntity));
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    @PostMapping("/generate/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<SubTipoProductoEntity> generate(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(subTipoProductoService.generate(id));
    }

    @GetMapping("/TipoProducto/{id}")
    public ResponseEntity<List<SubTipoProductoEntity>> getByTipoProducto(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(subTipoProductoService.getByTipoProducto(id));
    }

    @GetMapping("/TipoProducto/codigo/{codigo}")
    public ResponseEntity<List<SubTipoProductoEntity>> getSubTiposProductosByCodigo(@PathVariable(value = "codigo") String codigo){
        return ResponseEntity.ok(subTipoProductoService.getSubTiposProductosByCodigo(codigo));
    }
}
