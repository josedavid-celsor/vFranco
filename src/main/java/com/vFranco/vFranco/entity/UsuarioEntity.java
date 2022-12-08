package com.vFranco.vFranco.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="usuario")
public class UsuarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @Column(name = "dni")
    String dni;

    @Column(name = "nombre")
    String nombre;

    @Column(name = "apellido")
    String apellido;

    @Column(name = "apellido2")
    String apellido2;

    @Column(name = "email")
    String email;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Column(name = "token")
    String token;

    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idAuthoritys")
    private AuthoritysEntity authoritys;

    public UsuarioEntity(Long id, String dni, String nombre, String apellido, String apellido2, String email,
            String username, String password, String token, AuthoritysEntity authoritys) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.apellido2 = apellido2;
        this.email = email;
        this.username = username;
        this.password = password;
        this.token = token;
        this.authoritys = authoritys;
    }


    public UsuarioEntity(Long id, String username, String password, AuthoritysEntity authoritys) {
        this.id = id;
        this.username = username;
        this.password = password;

        this.authoritys = authoritys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AuthoritysEntity getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(AuthoritysEntity authoritys) {
        this.authoritys = authoritys;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
