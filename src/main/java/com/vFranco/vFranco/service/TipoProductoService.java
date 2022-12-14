package com.vFranco.vFranco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vFranco.vFranco.entity.TipoProductoEntity;
import com.vFranco.vFranco.exception.ResourceNotFoundException;
import com.vFranco.vFranco.exception.ResourceNotModifiedException;
import com.vFranco.vFranco.helper.RandomHelper;
import com.vFranco.vFranco.helper.ValidationHelper;
import com.vFranco.vFranco.repository.TipoProductoRepository;
import com.vFranco.vFranco.request.CreateTPRequest;

@Service
public class TipoProductoService {
    
    @Autowired
    TipoProductoRepository tipoProductoRepository;
    

    private final String[] TIPO = {"Productos Químicos", "Celulosas y textiles", "Complementos de Higuiene",  
                                  "Maquina de Limpieza"};
    private final String[] CARATERISTICA = {"Ambientadores y desodorantes", "Celulosa Industrial", "Útiles de Limpieza", 
                                          "Inyección/Extracción"};
    //Constructor
    public TipoProductoService(TipoProductoRepository tipoProductoRepository) {
      this.tipoProductoRepository = tipoProductoRepository;
  }
  //validaciones
  public void validate(Long id) {
        if (!tipoProductoRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(TipoProductoEntity oTipoproductoEntity) {
        ValidationHelper.validateStringLength(oTipoproductoEntity.getNombre(), 2, 100, "campo nombre Tipoproducto (el campo debe tener longitud de 2 a 100 caracteres)");
    }

    //Crear tipoP
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

      //Get
     public TipoProductoEntity get(@PathVariable(value = "id") Long id) {
        if (tipoProductoRepository.existsById(id)) {
            return tipoProductoRepository.findById(id).orElseThrow(null);
        } else {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    //Delete
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

  //Update
  public TipoProductoEntity update(TipoProductoEntity tipoProductoBDDEntity, TipoProductoEntity tipoProductoEntity) {
    tipoProductoBDDEntity.setNombre(tipoProductoEntity.getNombre());
    return tipoProductoRepository.save(tipoProductoBDDEntity);
}

public TipoProductoEntity generate() {
  String nombre = TIPO[RandomHelper.getRandomInt(0, TIPO.length - 1)] + " " + CARATERISTICA[RandomHelper.getRandomInt(0, CARATERISTICA.length - 1)];
  TipoProductoEntity tipoProductoEntity = new TipoProductoEntity();
  tipoProductoEntity.setNombre(nombre);
  return tipoProductoEntity;
}

public Long generateSome(@PathVariable(value = "amount") int amount) {
  
  for (int i = 0; i < amount; i++) {
      TipoProductoEntity tipoProductoEntity = generate();
      tipoProductoRepository.save(tipoProductoEntity);
  }
  return tipoProductoRepository.count();
}


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

}
