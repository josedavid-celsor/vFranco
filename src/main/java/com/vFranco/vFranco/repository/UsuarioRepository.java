package com.vFranco.vFranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vFranco.vFranco.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
    UsuarioEntity findByUsernameAndPassword(String username, String password);
    UsuarioEntity findByUsername(String username);

    @Query("select exists(select 1 from usuario where username = ?1)")
    public boolean existsByUsername(String username);

    @Query("select exists(select 1 from usuario where email = ?1)")
    public boolean existsByEmail(String email);
}
 