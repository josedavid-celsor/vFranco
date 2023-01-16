package com.vFranco.vFranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.entity.CompraEntity;
import com.vFranco.vFranco.entity.FacturaEntity;
import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.repository.CompraRepository;

@Service
public class CompraService {
    @Autowired
    CompraRepository compraRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    FacturaService facturaService;

    public CompraEntity save(CompraEntity compraEntity) {
        return compraRepository.save(compraEntity);
    }

    public List<CompraEntity> getAllByUsuario(String username) {
        UsuarioEntity usuario = usuarioService.findbyUsername(username);
        return compraRepository.findAllByUsuario(usuario);

    }

    
  public List<CompraEntity> getByFactura(Long id) {
    FacturaEntity factura = facturaService.get(id);
    return compraRepository.findByFactura(factura);
   
  }
}
