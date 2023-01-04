package com.vFranco.vFranco.request;

import com.vFranco.vFranco.entity.TipoProductoEntity;

public class CreateProductoRequest {
    
    private String codigo;
    private String nombre;
    private int cantidad;
    private double precio;
    private TipoProductoEntity tipoProducto;

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
    public TipoProductoEntity getTipoProducto() {
        return tipoProducto;
    }
    public void setTipoProducto(TipoProductoEntity tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    
    
}
