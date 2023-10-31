package com.spring.animal.hotel.spring.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.animal.hotel.spring.models.BookingModel;

public interface BookingRepository extends JpaRepository<BookingModel, UUID> {
    
}
