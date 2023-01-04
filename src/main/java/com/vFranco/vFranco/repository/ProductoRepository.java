package com.vFranco.vFranco.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vFranco.vFranco.entity.ProductoEntity;
import com.vFranco.vFranco.entity.TipoProductoEntity;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
     
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM ProductoEntity u WHERE u.nombre = :nombre")
    boolean existsByName(@Param("nombre") String name);

    Page<ProductoEntity> findByNombreIgnoreCaseContainingOrCodigoIgnoreCaseContaining(String nombre, String codigo, Pageable oPageable);

    Page<ProductoEntity> findByTipoProducto(TipoProductoEntity id_tipoproducto, Pageable oPageable);

    @Query(value = "SELECT * FROM producto WHERE id_tipoproducto = ?1 AND (nombre LIKE  %?2% OR codigo LIKE %?3%)", nativeQuery = true)
    Page<ProductoEntity> findByTipoProductoAndNombreOrCodigo(TipoProductoEntity id_tipoproducto, String nombre, String codigo, Pageable oPageable);
} 
