package com.vFranco.vFranco.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vFranco.vFranco.entity.TipoProductoEntity;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProductoEntity, Long>{
    
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM TipoProductoEntity u WHERE u.nombre = :nombre")
    boolean existsByName(@Param("nombre") String name);

    public Page<TipoProductoEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM SubTipoProductoEntity u WHERE u.codigo = :codigo")
    boolean existsByCodigo(@Param("codigo") String codigo);
}
