package com.vFranco.vFranco.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Pageable;
import com.vFranco.vFranco.entity.FacturaEntity;
import com.vFranco.vFranco.exception.ResourceNotFoundException;
import com.vFranco.vFranco.exception.ResourceNotModifiedException;
import com.vFranco.vFranco.helper.ValidationHelper;
import com.vFranco.vFranco.repository.FacturaRepository;

@Service
public class FacturaService {
    @Autowired
    FacturaRepository facturaRepository;

    public FacturaEntity save(FacturaEntity facturaEntity){
        return facturaRepository.save(facturaEntity);
    }

    public Page<FacturaEntity> getPage(Pageable oPageable, String strFilter) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<FacturaEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = facturaRepository.findAll(oPageable);
        } 
        return oPage;
      }

      //Delete
      public Long delete(@PathVariable(value = "id") Long id) {
      
        if (facturaRepository.existsById(id)) {
            facturaRepository.deleteById(id);
            if (facturaRepository.existsById(id)) {
                throw new ResourceNotModifiedException("Can't remove register " + id);
            } else {
                return id;
            }
        } else {
            return 0L;
        }
    }

     //Get
     public FacturaEntity get(@PathVariable(value = "id") Long id) {
        if (facturaRepository.existsById(id)) {
            return facturaRepository.findById(id).orElseThrow(null);
        } else {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
}
