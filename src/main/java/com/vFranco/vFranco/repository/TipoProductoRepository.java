package com.vFranco.vFranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vFranco.vFranco.entity.TipoProductoEntity;

public interface TipoProductoRepository extends JpaRepository<TipoProductoEntity, Long> {
    
}
