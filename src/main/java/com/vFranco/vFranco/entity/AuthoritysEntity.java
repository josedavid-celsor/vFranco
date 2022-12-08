package com.vFranco.vFranco.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="authoritys")
public class AuthoritysEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nombre")
    String nombre;

    @OneToMany(mappedBy = "authoritys", fetch = FetchType.LAZY)
    private final List<UsuarioEntity> usuarios;

    public AuthoritysEntity(Long id, String nombre) {
        this.usuarios = new ArrayList<>();
        this.id = id;
        this.nombre = nombre;
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

    public int getUsuarios() {
        return usuarios.size();
    }
    
}
