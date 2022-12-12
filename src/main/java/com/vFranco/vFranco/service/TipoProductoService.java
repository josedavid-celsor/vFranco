package com.vFranco.vFranco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.vFranco.vFranco.entity.TipoProductoEntity;
import com.vFranco.vFranco.exception.ResourceNotFoundException;
import com.vFranco.vFranco.repository.TipoProductoRepository;
import com.vFranco.vFranco.request.CreateTPRequest;

@Service
public class TipoProductoService {
    
    @Autowired
    TipoProductoRepository tipoProductoRepository;
    
    
    public TipoProductoService(TipoProductoRepository tipoProductoRepository) {
      this.tipoProductoRepository = tipoProductoRepository;
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
}
