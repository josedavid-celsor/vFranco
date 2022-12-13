package com.vFranco.vFranco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.vFranco.vFranco.entity.TipoProductoEntity;
import com.vFranco.vFranco.exception.ResourceNotFoundException;
import com.vFranco.vFranco.exception.ResourceNotModifiedException;
import com.vFranco.vFranco.helper.ValidationHelper;
import com.vFranco.vFranco.repository.TipoProductoRepository;
import com.vFranco.vFranco.request.CreateTPRequest;

@Service
public class TipoProductoService {
    
    @Autowired
    TipoProductoRepository tipoProductoRepository;
    
    
    public TipoProductoService(TipoProductoRepository tipoProductoRepository) {
      this.tipoProductoRepository = tipoProductoRepository;
  }

  public void validate(Long id) {
        if (!tipoProductoRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(TipoProductoEntity oTipoproductoEntity) {
        ValidationHelper.validateStringLength(oTipoproductoEntity.getNombre(), 2, 100, "campo nombre Tipoproducto (el campo debe tener longitud de 2 a 100 caracteres)");
    }
    public TipoProductoEntity creaTipoProducto(CreateTPRequest createTPRequest){
      
        TipoProductoEntity tipoProducto = new TipoProductoEntity();
        
        tipoProducto.setNombre(createTPRequest.getNombre());
        return tipoProductoRepository.save(tipoProducto);
      }

    public boolean existByName(String name){
        try{
          return tipoProductoRepository.existsByName(name);
        }catch(Exception e){
          return false;
        }
       
      }

     public TipoProductoEntity get(@PathVariable(value = "id") Long id) {
        if (tipoProductoRepository.existsById(id)) {
            return tipoProductoRepository.findById(id).orElseThrow(null);
        } else {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long delete(@PathVariable(value = "id") Long id) {
      
      if (tipoProductoRepository.existsById(id)) {
        tipoProductoRepository.deleteById(id);
          if (tipoProductoRepository.existsById(id)) {
              throw new ResourceNotModifiedException("Can't remove register " + id);
          } else {
              return id;
          }
      } else {
          return 0L;
      }
  }

  public Long update(Long id, TipoProductoEntity tipoProductoEntity) {
   
    tipoProductoEntity.setId(id);
    validate(id);
    validate(tipoProductoEntity);
    return tipoProductoRepository.save(tipoProductoEntity).getId();
}
}
