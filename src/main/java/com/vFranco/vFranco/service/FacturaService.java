package com.vFranco.vFranco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vFranco.vFranco.entity.FacturaEntity;
import com.vFranco.vFranco.repository.FacturaRepository;

@Service
public class FacturaService {
    @Autowired
    FacturaRepository facturaRepository;

    public FacturaEntity save(FacturaEntity facturaEntity){
        return facturaRepository.save(facturaEntity);
    }
}
