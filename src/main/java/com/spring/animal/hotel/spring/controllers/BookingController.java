package com.spring.animal.hotel.spring.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
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
import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.repositories.BookingRepository;
import com.spring.animal.hotel.spring.repositories.ClientRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ClientRepository clientRepository;

    @PostMapping
    public ResponseEntity<Object> saveBooking(@RequestBody @Valid BookingDto bookingDto) {
        BookingModel bookingModel = new BookingModel();
        BeanUtils.copyProperties(bookingDto, bookingModel);

        Optional<ClientModel> optionalClient = clientRepository.findById(bookingDto.id_client_bo());
        if (optionalClient.isPresent()) {
            bookingModel.setClientModel(optionalClient.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O registro da chave estrangeira fornecida não existe.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingRepository.save(bookingModel));
    }
    
    @GetMapping
    public ResponseEntity<List<BookingModel>> getAllBookings() {
        List<BookingModel> bookingList = bookingRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(bookingList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneBooking(@PathVariable(value = "id") UUID id) {
        Optional<BookingModel> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalBooking.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro não foi encrontrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookingDto bookingDto) {
        Optional<BookingModel> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            BookingModel bookingModel = optionalBooking.get();
            BeanUtils.copyProperties(bookingDto, bookingModel);

            return ResponseEntity.status(HttpStatus.OK).body(bookingRepository.save(bookingModel));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro escolhido não existe.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable(value = "id") UUID id) {
        Optional<BookingModel> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            bookingRepository.delete(optionalBooking.get());

            return ResponseEntity.status(HttpStatus.OK).body("Deleção bem sucedida.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro escolhido não existe.");
    }
}
