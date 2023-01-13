package com.vFranco.vFranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vFranco.vFranco.entity.SubTipoProductoEntity;

@Repository
public interface SubTipoProductoRepository extends JpaRepository<SubTipoProductoEntity, Long>{
    
}
