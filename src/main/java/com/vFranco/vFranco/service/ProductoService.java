package com.vFranco.vFranco.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import com.vFranco.vFranco.entity.ProductoEntity;
import com.vFranco.vFranco.exception.ResourceNotFoundException;
import com.vFranco.vFranco.helper.ValidationHelper;
import com.vFranco.vFranco.repository.ProductoRepository;
import com.vFranco.vFranco.request.CreateProductoRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    
    @Autowired
    ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoEntity creaProducto(CreateProductoRequest createProductoRequest){
      
      ProductoEntity producto = new ProductoEntity();
      
      producto.setNombre(createProductoRequest.getNombre());
      return productoRepository.save(producto);
    }

    public boolean existByName(String name){
      try{
        return productoRepository.existsByName(name);
      }catch(Exception e){
        return false;
      }
    }

    public ProductoEntity get(@PathVariable(value = "id") Long id) {
      if (productoRepository.existsById(id)) {
          return productoRepository.findById(id).orElseThrow(null);
      } else {
          throw new ResourceNotFoundException("id " + id + " not exist");
      }
  }

  public Page<ProductoEntity> getPage(Pageable oPageable, String strFilter, Long lTipoProducto) {
    ValidationHelper.validateRPP(oPageable.getPageSize());
    Page<ProductoEntity> oPage = null;
    if (lTipoProducto == null) {
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = productoRepository.findAll(oPageable);
        } else {
            oPage = productoRepository.findByNombreIgnoreCaseContainingOrCodigoIgnoreCaseContaining(strFilter, strFilter, oPageable);
        }
    } else {
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = productoRepository.findByTipoProducto(lTipoProducto, oPageable);
        } else {
            oPage = productoRepository.findByTipoProductoAndNombreOrCodigo(lTipoProducto, strFilter, strFilter, oPageable);
        }
    }
    return oPage;
}
}