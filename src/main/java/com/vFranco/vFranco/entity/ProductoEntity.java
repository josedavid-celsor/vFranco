package com.vFranco.vFranco.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "producto")

public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio")
    private double precio;

    @Column(name = "images")
    private String images;

    @ManyToOne
    @JoinColumn(name = "subtipoproducto_id")
    private SubTipoProductoEntity subTipoProducto;

    @OneToMany(mappedBy = "producto")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private final List<CarritoEntity> carritos;

    @OneToMany(mappedBy = "producto")
    private final List<CompraEntity> compras;


    public ProductoEntity() {
        this.carritos = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }



    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public SubTipoProductoEntity getSubTipoProducto() {
        return subTipoProducto;
    }

    public void setSubTipoProducto(SubTipoProductoEntity subTipoProducto) {
        this.subTipoProducto = subTipoProducto;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }



}
