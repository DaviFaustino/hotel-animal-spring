package com.spring.animal.hotel.spring.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.animal.hotel.spring.models.BookingDto;
import com.spring.animal.hotel.spring.models.BookingModel;
import com.spring.animal.hotel.spring.services.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity<Object> saveBooking(@RequestBody @Valid BookingDto bookingDto) {
        Object serviceResponse = bookingService.saveBooking(bookingDto);

        if (serviceResponse.getClass() == BookingModel.class) {
            return ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
    }
    
    @GetMapping
    public ResponseEntity<List<BookingModel>> getAllBookings() {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneBooking(@PathVariable(value = "id") UUID id) {
        Object serviceResponse = bookingService.getOneBooking(id);

        if (serviceResponse.getClass() == BookingModel.class) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookingDto bookingDto) {
        Object serviceResponse = bookingService.updateBooking(bookingDto, id);

        if (serviceResponse.getClass() == BookingModel.class) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable(value = "id") UUID id) {
        if (bookingService.deleteBooking(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleção bem sucedida.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro escolhido não existe.");
    }
}
