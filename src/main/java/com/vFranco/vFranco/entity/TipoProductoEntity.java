package com.vFranco.vFranco.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="tipoproducto")
public class TipoProductoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "tipoProducto")
    private List<SubTipoProductoEntity> subTipoProducto;

    public TipoProductoEntity(){
        
    }

    public TipoProductoEntity(Long id, String nombre, List<SubTipoProductoEntity> subTipoProducto) {
        this.id = id;
        this.nombre = nombre;
        this.subTipoProducto = subTipoProducto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
/* 
    public List<ProductoEntity> getProductos() {
        return productos;
    } */

   /*  public void setProductos(List<ProductoEntity> productos) {
        this.productos = productos;
    } */

    
}
