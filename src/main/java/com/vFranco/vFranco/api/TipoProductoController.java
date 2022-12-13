package com.vFranco.vFranco.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vFranco.vFranco.entity.TipoProductoEntity;
import com.vFranco.vFranco.helper.ValidationHelper;
import com.vFranco.vFranco.repository.TipoProductoRepository;
import com.vFranco.vFranco.request.CreateTPRequest;
import com.vFranco.vFranco.service.TipoProductoService;

@RestController
@Controller
@RequestMapping("/TipoProducto")
public class TipoProductoController {
    
    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

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

    @GetMapping("/getPage")
    public Page<TipoProductoEntity> getPage(Pageable oPageable, String strFilter) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<TipoProductoEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = tipoProductoRepository.findAll(oPageable);
        } else {
            oPage = tipoProductoRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        TipoProductoEntity tipoProductoEntity = tipoProductoService.get(id);
        if(tipoProductoEntity != null){
            tipoProductoService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    @PutMapping("/")
    public ResponseEntity<Long> update(@RequestBody TipoProductoEntity tipoProductoEntity) {
        return new ResponseEntity<Long>(tipoProductoService.update(tipoProductoEntity.getId(), tipoProductoEntity), HttpStatus.OK);
    }
}
