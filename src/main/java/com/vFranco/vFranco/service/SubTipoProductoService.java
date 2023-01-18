package com.vFranco.vFranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.vFranco.vFranco.entity.SubTipoProductoEntity;
import com.vFranco.vFranco.entity.TipoProductoEntity;
import com.vFranco.vFranco.exception.ResourceNotFoundException;
import com.vFranco.vFranco.exception.ResourceNotModifiedException;
import com.vFranco.vFranco.helper.RandomHelper;
import com.vFranco.vFranco.helper.ValidationHelper;
import com.vFranco.vFranco.repository.SubTipoProductoRepository;
import com.vFranco.vFranco.request.CreateSTPRequest;

@Service
public class SubTipoProductoService {

    @Autowired
    SubTipoProductoRepository subTipoProductoRepository;

    
    @Autowired
    TipoProductoService tipoProductoService;

    private final String[] NOMBRE = {"Ambientadores y desodorantes", "Celulosa Industrial", "Útiles de limpieza LEWI",  
                                  "Inyección"};

    private final String[] CODIGO = {"AMBIENTADORESYDESODORANTES", "CELULOSAINDUSTRIAL", "UTILESDELIMPIEZALEWI", "INYECCION"};

    public SubTipoProductoEntity creaSubTipoProducto(CreateSTPRequest createSTPRequest){
      SubTipoProductoEntity subTipoProducto = new SubTipoProductoEntity();
      subTipoProducto.setNombre(createSTPRequest.getNombre());
      subTipoProducto.setCodigo(createSTPRequest.getCodigo());
      subTipoProducto.setTipoProducto(createSTPRequest.getTipoProducto());
      return subTipoProductoRepository.save(subTipoProducto);
    }

    public SubTipoProductoService(SubTipoProductoRepository subTipoProductoRepository) {
      this.subTipoProductoRepository = subTipoProductoRepository;
  }

   //Get
   public SubTipoProductoEntity get(@PathVariable(value = "id") Long id) {
    if (subTipoProductoRepository.existsById(id)) {
        return subTipoProductoRepository.findById(id).orElseThrow(null);
    } else {
        throw new ResourceNotFoundException("id " + id + " not exist");
    }
  }

    //Delete
    public Long delete(@PathVariable(value = "id") Long id) {
      if (subTipoProductoRepository.existsById(id)) {
        subTipoProductoRepository.deleteById(id);
          if (subTipoProductoRepository.existsById(id)) {
              throw new ResourceNotModifiedException("Can't remove register " + id);
          } else {
              return id;
          }
      } else {
          return 0L;
      }
  }
    
    public SubTipoProductoEntity getOneRandom() {
        List<SubTipoProductoEntity> listadosubTipos = subTipoProductoRepository.findAll();
        int iPosicion = RandomHelper.getRandomInt(0,  listadosubTipos.size()-1);
        return listadosubTipos.get(iPosicion);
      }

      public boolean existByName(String name){
        try{
          return subTipoProductoRepository.existsByName(name);
        }catch(Exception e){
          return false;
        }
       
      }

    public List<SubTipoProductoEntity> getByTipoProducto(Long tipoid){
      TipoProductoEntity tipo = tipoProductoService.get(tipoid);

      return subTipoProductoRepository.findByTipoProducto(tipo);
    }

    public Page<SubTipoProductoEntity> getPage(Pageable oPageable, String strFilter) {
      ValidationHelper.validateRPP(oPageable.getPageSize());
      Page<SubTipoProductoEntity> oPage = null;
      if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
          oPage = subTipoProductoRepository.findAll(oPageable);
      } else {
          oPage = subTipoProductoRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
      }
      return oPage;
    }

      //Update
  public SubTipoProductoEntity update(SubTipoProductoEntity subTipoProductoBDDEntity, SubTipoProductoEntity subTipoProductoEntity) {
    subTipoProductoBDDEntity.setNombre(subTipoProductoEntity.getNombre());
    subTipoProductoBDDEntity.setCodigo(subTipoProductoEntity.getCodigo());
    return subTipoProductoRepository.save(subTipoProductoBDDEntity);
}

public SubTipoProductoEntity generate(Long id) {
  String nombre = NOMBRE[RandomHelper.getRandomInt(0, NOMBRE.length - 1)];
  String codigo = CODIGO[RandomHelper.getRandomInt(0, CODIGO.length - 1)];
  SubTipoProductoEntity subTipoProductoEntity = new SubTipoProductoEntity();
  subTipoProductoEntity.setNombre(nombre);
  subTipoProductoEntity.setCodigo(codigo);
  subTipoProductoEntity.setTipoProducto(tipoProductoService.get(id));
  return subTipoProductoRepository.save(subTipoProductoEntity);
}

public List<SubTipoProductoEntity> getSubTiposProductosByCodigo(String codigo){
  TipoProductoEntity tipoProductoEntity = tipoProductoService.findByCodigo(codigo);
  return subTipoProductoRepository.findByTipoProducto(tipoProductoEntity);
}
}
