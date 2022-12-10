package com.vFranco.vFranco.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="usuario")
public class UsuarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "dni")
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "apellido2")
    private String apellido2;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "token")
    private String token;

    
    
    @ManyToOne
    @JoinColumn(name = "idAuthority")
    private AuthoritysEntity authority;


    //CAMBIOS
    @OneToMany(mappedBy = "usuario")
    private Set<CarritoEntity> carritos;

    public UsuarioEntity(){

    }

    public UsuarioEntity(Long id, String dni, String nombre, String apellido, String apellido2, String email,
            String username, String password, String token, AuthoritysEntity authority, Set<CarritoEntity> carritos) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.apellido2 = apellido2;
        this.email = email;
        this.username = username;
        this.password = password;
        this.token = token;
        this.authority = authority;
        this.carritos = carritos;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getDni() {
        return dni;
    }


    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getApellido() {
        return apellido;
    }


    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public String getApellido2() {
        return apellido2;
    }


    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }


    public AuthoritysEntity getAuthority() {
        return authority;
    }


    public void setAuthority(AuthoritysEntity authority) {
        this.authority = authority;
    }


    public Set<CarritoEntity> getCarritos() {
        return carritos;
    }


    public void setCarritos(Set<CarritoEntity> carritos) {
        this.carritos = carritos;
    }

   
    
    
}
