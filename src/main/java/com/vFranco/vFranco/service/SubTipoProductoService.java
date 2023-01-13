package com.vFranco.vFranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.entity.SubTipoProductoEntity;
import com.vFranco.vFranco.helper.RandomHelper;
import com.vFranco.vFranco.repository.SubTipoProductoRepository;

@Service
public class SubTipoProductoService {

    @Autowired
    SubTipoProductoRepository subTipoProductoRepository;

    public SubTipoProductoService(SubTipoProductoRepository subTipoProductoRepository) {
      this.subTipoProductoRepository = subTipoProductoRepository;
  }
    
    public SubTipoProductoEntity getOneRandom() {
        List<SubTipoProductoEntity> listadosubTipos = subTipoProductoRepository.findAll();
        int iPosicion = RandomHelper.getRandomInt(0,  listadosubTipos.size()-1);
        return listadosubTipos.get(iPosicion);
      }
}
