package com.vFranco.vFranco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.entity.UsuarioEntity;
import com.vFranco.vFranco.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioEntity findbyUsername(String username){
        return usuarioRepository.findByUsername(username);
    }
}
