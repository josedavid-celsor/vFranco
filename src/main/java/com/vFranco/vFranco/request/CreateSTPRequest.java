package com.vFranco.vFranco.request;

import com.vFranco.vFranco.entity.TipoProductoEntity;

public class CreateSTPRequest {
    String nombre;
    String codigo;
    private TipoProductoEntity TipoProducto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public TipoProductoEntity getTipoProducto() {
        return TipoProducto;
    }

    public void setTipoProducto(TipoProductoEntity tipoProducto) {
        TipoProducto = tipoProducto;
    }
    
    
}
