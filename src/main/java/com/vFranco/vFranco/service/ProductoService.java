/*  package com.vFranco.vFranco.service;



import org.springframework.beans.factory.annotation.Autowired;

import com.vFranco.vFranco.entity.ProductoEntity;
import com.vFranco.vFranco.repository.ProductoRepository;
import com.vFranco.vFranco.request.CreateProductoRequest;

public class ProductoService {
    
    @Autowired
    ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoEntity creaProducto(CreateProductoRequest createProductoRequest){
      
       ProductoEntity producto = new ProductoEntity();
        
       producto.setNombre(createProductoRequest.getNombre());
        return ProductoRepository.save(producto);
      }

    public boolean existByName(String name){
        try{
          return productoRepository.existsByName(name);
        }catch(Exception e){
          return false;
        } 
      }
}
  */