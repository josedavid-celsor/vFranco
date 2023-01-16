package com.vFranco.vFranco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vFranco.vFranco.entity.CompraEntity;
import com.vFranco.vFranco.entity.FacturaEntity;
import com.vFranco.vFranco.entity.UsuarioEntity;

public interface CompraRepository extends JpaRepository<CompraEntity, Long> {

    @Query(value = "SELECT * FROM compra WHERE factura_id IN (SELECT id FROM factura WHERE usuario_id IN (SELECT id FROM usuario WHERE id = ?1))", nativeQuery = true)
    public List<CompraEntity> findAllByUsuario(UsuarioEntity usuario);

    public List<CompraEntity> findByFactura(FacturaEntity factura);
    
}
