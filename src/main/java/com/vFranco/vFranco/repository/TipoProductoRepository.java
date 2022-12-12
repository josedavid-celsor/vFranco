package com.vFranco.vFranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vFranco.vFranco.entity.TipoProductoEntity;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProductoEntity, Long> {
    
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM TipoProductoEntity u WHERE u.nombre = :nombre")
    boolean existsByName(@Param("nombre") String name);
}
