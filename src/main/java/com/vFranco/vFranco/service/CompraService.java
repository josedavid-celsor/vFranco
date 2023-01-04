package com.vFranco.vFranco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.entity.CompraEntity;
import com.vFranco.vFranco.repository.CompraRepository;

@Service
public class CompraService {
    @Autowired
    CompraRepository compraRepository;

    public CompraEntity save(CompraEntity compraEntity){
        return compraRepository.save(compraEntity);
    }
}
