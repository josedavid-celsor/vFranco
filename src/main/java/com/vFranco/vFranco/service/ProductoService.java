package com.vFranco.vFranco.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;

import com.vFranco.vFranco.entity.ProductoEntity;
import com.vFranco.vFranco.entity.SubTipoProductoEntity;
import com.vFranco.vFranco.entity.TipoProductoEntity;
import com.vFranco.vFranco.exception.ResourceNotFoundException;
import com.vFranco.vFranco.exception.ResourceNotModifiedException;
import com.vFranco.vFranco.helper.RandomHelper;
import com.vFranco.vFranco.helper.ValidationHelper;
import com.vFranco.vFranco.repository.ProductoRepository;
import com.vFranco.vFranco.request.CreateProductoRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    TipoProductoService tipoProductoService;
    @Autowired
    SubTipoProductoService subtipoProductoService;

    private final String[] NOMBRE = {"Ambientadores y desodorantes", "Celulosa Industrial", "Útiles de Limpieza LEWI",  
                                  "Aspiradoras de Polvo"};
    private final String[] CODIGO = {"154541fdsafd","fdsafdsa5454","fdsafdsa5484848854","87481564dsaf"};
    private final String[] FOTOS = {"ambientador.jpg", "desengrasante.jpg", "opubwnpvjue.jpeg", "panuelo.jpg"};

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoEntity creaProducto(CreateProductoRequest createProductoRequest, String fotos){
      
      ProductoEntity producto = new ProductoEntity();
      
      producto.setNombre(createProductoRequest.getNombre());
      producto.setCodigo(createProductoRequest.getCodigo());
      producto.setPrecio(createProductoRequest.getPrecio());
      producto.setCantidad(createProductoRequest.getCantidad());
      producto.setSubTipoProducto(createProductoRequest.getSubTipoProducto());
      producto.setImages(fotos);
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

  public Page<ProductoEntity> getPage(Pageable oPageable, String strFilter, String codigoTipoProducto, String codigoSubTipo) {
    ValidationHelper.validateRPP(oPageable.getPageSize());
    Page<ProductoEntity> oPage = null;
    if (codigoSubTipo == null && codigoTipoProducto != null) {
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            TipoProductoEntity tipoProductoEntity = this.tipoProductoService.getByCodigo(codigoTipoProducto);
            oPage = productoRepository.findByTipoProducto(tipoProductoEntity ,oPageable);
        } else {
            TipoProductoEntity tipoProductoEntity = this.tipoProductoService.getByCodigo(codigoTipoProducto);
            oPage = productoRepository.findByTipoProductoAndNombreOrCodigo(tipoProductoEntity, strFilter, strFilter, oPageable);
        }
    } else if(codigoTipoProducto != null && codigoSubTipo != null) {
        if(strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()){
            SubTipoProductoEntity subTipoProductoEntity = this.subtipoProductoService.getByCodigo(codigoSubTipo);
            oPage = productoRepository.findBySubTipoProducto(subTipoProductoEntity, oPageable);
        }else{
            SubTipoProductoEntity subTipoProductoEntity = this.subtipoProductoService.getByCodigo(codigoSubTipo);
            oPage = productoRepository.findByNombreAndSubTipoProductoOrCodigo(subTipoProductoEntity, strFilter, strFilter, oPageable);
        }
    }else{
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = productoRepository.findAll(oPageable);
        } else {
            oPage = productoRepository.findByNombreIgnoreCaseContainingOrCodigoIgnoreCaseContaining(strFilter, strFilter, oPageable);
        }
    }
    return oPage;
}

//Update
public ProductoEntity update(ProductoEntity productoBDDEntity, ProductoEntity productoEntity) {
    productoBDDEntity.setNombre(productoEntity.getNombre());
    productoBDDEntity.setCantidad(productoEntity.getCantidad());
    productoBDDEntity.setCodigo(productoEntity.getCodigo());
    productoBDDEntity.setSubTipoProducto(productoEntity.getSubTipoProducto());
    productoBDDEntity.setPrecio(productoEntity.getPrecio());
    productoBDDEntity.setImages(productoEntity.getImages());
    return productoRepository.save(productoBDDEntity);
}

 //Delete
 public Long delete(@PathVariable(value = "id") Long id) {
    if (productoRepository.existsById(id)) {
        productoRepository.deleteById(id);
        if (productoRepository.existsById(id)) {
            throw new ResourceNotModifiedException("Can't remove register " + id);
        } else {
            return id;
        }
    } else {
        return 0L;
    }
}

public ProductoEntity generate() {
    ProductoEntity productoEntity = new ProductoEntity();
    String nombre = NOMBRE[RandomHelper.getRandomInt(0, NOMBRE.length -1)];
    String codigo = CODIGO[RandomHelper.getRandomInt(0, CODIGO.length -1)];
    String fotos = FOTOS[RandomHelper.getRandomInt(0, CODIGO.length -1)];
    productoEntity.setNombre(nombre);
    productoEntity.setCodigo(codigo);
    productoEntity.setCantidad(RandomHelper.getRandomInt(0, 100));
    productoEntity.setPrecio(RandomHelper.getRadomDouble(0, 100));
    productoEntity.setImages(fotos);
    productoEntity.setSubTipoProducto(subtipoProductoService.getOneRandom());
    return productoRepository.save(productoEntity);
  } 
  
public Long generateSome(@PathVariable(value = "amount") int amount) {
    for (int i = 0; i < amount; i++) {
        ProductoEntity productoEntity = generate();
        productoRepository.save(productoEntity);
    }
    return productoRepository.count();
  } 
  
  public ProductoEntity getOneRandom() {
    List<ProductoEntity> listadoproductos = productoRepository.findAll();
    int iPosicion = RandomHelper.getRandomInt(0,  listadoproductos.size());
    return listadoproductos.get(iPosicion);
}
public ProductoEntity save(ProductoEntity productoEntity){
    return productoRepository.save(productoEntity);
}
}