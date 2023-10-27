package com.spring.animal.hotel.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.animal.hotel.spring.models.ClientModel;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Integer> {
    
}
