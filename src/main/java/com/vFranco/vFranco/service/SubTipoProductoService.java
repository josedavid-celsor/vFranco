package com.vFranco.vFranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.entity.SubTipoProductoEntity;
import com.vFranco.vFranco.entity.TipoProductoEntity;
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
}
