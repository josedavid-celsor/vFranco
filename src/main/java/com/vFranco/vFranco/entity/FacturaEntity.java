package com.vFranco.vFranco.entity;

import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="factura")
public class FacturaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "total_precio")
    private double totalPrecio;

    @Column(name = "iva")
    private double iva;

    @OneToMany(mappedBy = "factura")
    private final List<CompraEntity> compras;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;
    
    public FacturaEntity() {
        this.compras = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getCompras() {
        return compras.size();
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public double getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(double totalPrecio) {
        this.totalPrecio = totalPrecio;
    }


   
}
