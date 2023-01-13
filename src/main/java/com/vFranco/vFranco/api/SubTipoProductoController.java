package com.vFranco.vFranco.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vFranco.vFranco.entity.SubTipoProductoEntity;
import com.vFranco.vFranco.request.CreateSTPRequest;
import com.vFranco.vFranco.service.SubTipoProductoService;

@RestController
@Controller
@RequestMapping("/Subtipo")
public class SubTipoProductoController {
    @Autowired
    private SubTipoProductoService subTipoProductoService;



   /*  @PostMapping("/")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> createTipoProducto(@RequestBody CreateSTPRequest createRequest){

        if(subTipoProductoService.existByName(createRequest.getNombre())){
            return ResponseEntity.badRequest().body("name is already taken");
          }
        return ResponseEntity.ok(subTipoProductoService.creaSubTipoProducto(createRequest));

    } */

    /* @GetMapping("/{id}")
    public ResponseEntity<SubTipoProductoEntity> getTipoProducto(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(tipoProductoService.get(id));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Page<TipoProductoEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 5, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<Page<TipoProductoEntity>>(tipoProductoService.getPage(oPageable, strFilter), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        TipoProductoEntity tipoProductoEntity = tipoProductoService.get(id);
        if(tipoProductoEntity != null){
            tipoProductoService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<TipoProductoEntity> update(@RequestBody TipoProductoEntity tipoProductoEntity, @PathVariable(value = "id") Long id) {
        TipoProductoEntity tipoProductoBDDEntity = tipoProductoService.get(id);
        if(tipoProductoEntity != null){
            return ResponseEntity.ok(tipoProductoService.update(tipoProductoBDDEntity, tipoProductoEntity));
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<TipoProductoEntity> generate() {
        return ResponseEntity.ok(tipoProductoService.generate());
    }

    @PostMapping("/generate/{amount}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") int amount) {
        return ResponseEntity.ok(tipoProductoService.generateSome(amount));
    } */

    @GetMapping("/TipoProducto/{id}")
    public ResponseEntity<List<SubTipoProductoEntity>> getByTipoProducto(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(subTipoProductoService.getByTipoProducto(id));
    }
}
