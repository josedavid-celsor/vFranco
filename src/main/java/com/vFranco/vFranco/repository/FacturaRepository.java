package com.vFranco.vFranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vFranco.vFranco.entity.FacturaEntity;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long>{
    
}
