package com.vFranco.vFranco.entity;

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

@Entity
@Table(name="producto")

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
    
    @ManyToOne
    @JoinColumn(name = "idTipoProducto")
    private TipoProductoEntity idTipoProducto;

    @OneToMany(mappedBy = "producto")
    private final List<CarritoEntity> carritos;


    @OneToMany(mappedBy = "producto")
    private final List<CompraEntity> compras;
   

   

    public ProductoEntity(Long id, String codigo, String nombre, int cantidad, double precio,
            TipoProductoEntity idTipoProducto, List<CarritoEntity> carritos, List<CompraEntity> compras) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idTipoProducto = idTipoProducto;
        this.carritos = carritos;
        this.compras = compras;
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

    public TipoProductoEntity getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(TipoProductoEntity idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public List<CarritoEntity> getCarritos() {
        return carritos;
    }

    public List<CompraEntity> getCompras() {
        return compras;
    }

    
    
}
