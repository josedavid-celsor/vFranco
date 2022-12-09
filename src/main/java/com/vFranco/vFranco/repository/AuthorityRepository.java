package com.vFranco.vFranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vFranco.vFranco.entity.AuthoritysEntity;

public interface AuthorityRepository extends JpaRepository<AuthoritysEntity, Long>{
    
}
