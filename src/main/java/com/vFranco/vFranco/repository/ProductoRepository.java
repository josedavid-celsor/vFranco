package com.vFranco.vFranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vFranco.vFranco.entity.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    
}
