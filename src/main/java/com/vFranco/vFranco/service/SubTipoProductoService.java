package com.vFranco.vFranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.entity.SubTipoProductoEntity;
import com.vFranco.vFranco.entity.TipoProductoEntity;
import com.vFranco.vFranco.helper.RandomHelper;
import com.vFranco.vFranco.repository.SubTipoProductoRepository;

@Service
public class SubTipoProductoService {

    @Autowired
    SubTipoProductoRepository subTipoProductoRepository;

    
    @Autowired
    TipoProductoService tipoProductoService;

    public SubTipoProductoService(SubTipoProductoRepository subTipoProductoRepository) {
      this.subTipoProductoRepository = subTipoProductoRepository;
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
}
