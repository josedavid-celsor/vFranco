package com.vFranco.vFranco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vFranco.vFranco.entity.CarritoEntity;
import com.vFranco.vFranco.entity.UsuarioEntity;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long>{
    List<CarritoEntity> findByUsuario(UsuarioEntity user);
    Integer deleteAllByUsuario(UsuarioEntity user);
}