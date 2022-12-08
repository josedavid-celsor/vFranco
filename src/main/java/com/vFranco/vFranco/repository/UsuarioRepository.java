package com.vFranco.vFranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vFranco.vFranco.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
    UsuarioEntity findByUsernameAndPassword(String username, String password);
    UsuarioEntity findByUsername(String username);
}
 