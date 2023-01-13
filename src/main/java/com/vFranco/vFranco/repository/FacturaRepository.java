package com.vFranco.vFranco.repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vFranco.vFranco.entity.FacturaEntity;
import com.vFranco.vFranco.entity.UsuarioEntity;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long>{
   
    public Page<FacturaEntity> findAllByUsuario(UsuarioEntity usuario, Pageable oPageable);
}
