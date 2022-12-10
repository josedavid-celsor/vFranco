package com.vFranco.vFranco.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="carrito")

public class CarritoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioEntity usuario;

    @ManyToMany
    @JoinTable(
            name = "cartproducts",
            joinColumns = @JoinColumn(name = "idCarrito"),
            inverseJoinColumns = @JoinColumn(name = "idProduct")
    )
    private Set<ProductoEntity> productos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public Set<ProductoEntity> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductoEntity> productos) {
        this.productos = productos;
    }

    
}
