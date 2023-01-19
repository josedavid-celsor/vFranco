package com.vFranco.vFranco.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vFranco.vFranco.entity.SubTipoProductoEntity;
import com.vFranco.vFranco.entity.TipoProductoEntity;

@Repository
public interface SubTipoProductoRepository extends JpaRepository<SubTipoProductoEntity, Long>{
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM SubTipoProductoEntity u WHERE u.nombre = :nombre")
    boolean existsByName(@Param("nombre") String name);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM SubTipoProductoEntity u WHERE u.codigo = :codigo")
    boolean existsByCodigo(@Param("codigo") String codigo);

    @Query
    List<SubTipoProductoEntity> findByTipoProducto(TipoProductoEntity tipoproducto_id);
    
    public Page<SubTipoProductoEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable);

    public SubTipoProductoEntity findByCodigo(String codigo);
}
