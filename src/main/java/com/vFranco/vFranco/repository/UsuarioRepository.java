package com.vFranco.vFranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vFranco.vFranco.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
    UsuarioEntity findByUsernameAndPassword(String username, String password);
    UsuarioEntity findByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM UsuarioEntity u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM UsuarioEntity u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    UsuarioEntity findByVerificationCode(String verificationCode);
    UsuarioEntity findByNewPasswordCode(String passwordCode);
}
 