package com.vFranco.vFranco.service;

import java.time.LocalDateTime;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.entity.CarritoEntity;
import com.vFranco.vFranco.entity.CompraEntity;
import com.vFranco.vFranco.entity.FacturaEntity;
import com.vFranco.vFranco.entity.ProductoEntity;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.exception.ResourceNotModifiedException;
import com.vFranco.vFranco.repository.CarritoRepository;


@Service
public class CarritoService {
    @Autowired
    CarritoRepository carritoRepository;
    @Autowired
    ProductoService productoService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    FacturaService facturaService;
    @Autowired
    CompraService compraService;

    public CarritoEntity getOne(Long idCarrito){
        return carritoRepository.findById(idCarrito).orElse(null);
    }

    public List<CarritoEntity> getCarrito(String username){
        UsuarioEntity usuarioEntity =usuarioService.findbyUsername(username);
        return carritoRepository.findByUsuario(usuarioEntity);
    } 

    public void deleteOneProduct(Long idCarrito){
        if (carritoRepository.existsById(idCarrito)) {
        carritoRepository.deleteById(idCarrito);
        if (carritoRepository.existsById(idCarrito)) {
            throw new ResourceNotModifiedException("Can't remove carrito " + idCarrito);
        } 
    }
    }

    @Transactional
    public void deleteCarrito(String username){
        UsuarioEntity usuarioEntity =usuarioService.findbyUsername(username);
        if(usuarioEntity != null){
            carritoRepository.deleteAllByUsuario(usuarioEntity);
        }

    }

    @Transactional
    public void buyCarrito(String username, Long id){
        UsuarioEntity usuarioEntity =usuarioService.findbyUsername(username);
        List<CarritoEntity> listCarrito =  this.getCarrito(usuarioEntity.getUsername());
        FacturaEntity facturaEntity = new FacturaEntity();
        facturaEntity.setFecha(LocalDateTime.now());
        facturaEntity.setUsuario(usuarioEntity);
        if(id == 1){
            facturaEntity.setIva(21);
        }else{
            facturaEntity.setIva(7);
        }
        FacturaEntity facturaEntityBDD = facturaService.save(facturaEntity);
        Double totalPagado = 0D;
        for (CarritoEntity carritoEntity : listCarrito) {
            CompraEntity compraEntity = new CompraEntity();
            Double total = carritoEntity.getProducto().getPrecio()*carritoEntity.getCantidad() + (carritoEntity.getProducto().getPrecio()*carritoEntity.getCantidad() * (facturaEntity.getIva() / 100));
            compraEntity.setCantidad(carritoEntity.getCantidad());
            carritoEntity.getProducto().setCantidad(carritoEntity.getProducto().getCantidad()-carritoEntity.getCantidad());
            compraEntity.setPrecio(total);
            compraEntity.setFactura(facturaEntityBDD);
            compraEntity.setProducto(carritoEntity.getProducto());
            totalPagado += total;
            compraService.save(compraEntity);
            productoService.save(compraEntity.getProducto());
        }
        this.deleteCarrito(username);
        facturaEntityBDD.setTotalPrecio(totalPagado);
        facturaService.save(facturaEntityBDD);
    }

    public CarritoEntity insert(String username, Long idProducto){
        UsuarioEntity usuarioEntity = usuarioService.findbyUsername(username);
        ProductoEntity productoEntity = productoService.get(idProducto);
        CarritoEntity carritoEntity = carritoRepository.findByProductoAndUsuario(productoEntity, usuarioEntity);
        if(carritoEntity != null){
            carritoEntity.setCantidad(carritoEntity.getCantidad()+1);  
        }else{
            carritoEntity = new CarritoEntity();
            carritoEntity.setProducto(productoEntity);
            carritoEntity.setCantidad(1);
            carritoEntity.setUsuario(usuarioEntity);
        }
        return carritoRepository.save(carritoEntity);
    }
}
