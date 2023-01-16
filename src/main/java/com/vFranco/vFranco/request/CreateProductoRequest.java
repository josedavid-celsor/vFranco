package com.vFranco.vFranco.request;

import com.vFranco.vFranco.entity.SubTipoProductoEntity;
import com.vFranco.vFranco.entity.TipoProductoEntity;

public class CreateProductoRequest {

    private int id;
    
    private String codigo;
    private String nombre;


    private int cantidad;
    private double precio;
    private SubTipoProductoEntity subTipoProducto;
    private TipoProductoEntity TipoProducto;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public TipoProductoEntity getTipoProducto() {
        return TipoProducto;
    }
    public void setTipoProducto(TipoProductoEntity tipoProducto) {
        TipoProducto = tipoProducto;
    }

    
}
