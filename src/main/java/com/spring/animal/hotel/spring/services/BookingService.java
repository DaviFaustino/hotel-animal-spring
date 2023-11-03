package com.spring.animal.hotel.spring.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.animal.hotel.spring.models.BookingDto;
import com.spring.animal.hotel.spring.models.BookingModel;
import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.repositories.BookingRepository;
import com.spring.animal.hotel.spring.repositories.ClientRepository;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ClientRepository clientRepository;

    public Object saveBooking(BookingDto bookingDto) {
        BookingModel bookingModel = new BookingModel(bookingDto, null);

        Optional<ClientModel> optionalClient = clientRepository.findById(bookingDto.id_client_bo());

        if (optionalClient.isPresent()) {
            bookingModel.setClientModel(optionalClient.get());
        } else {
            return "O registro da chave estrangeira fornecida não existe.";
        }

        return bookingRepository.save(bookingModel);
    }

    public List<BookingModel> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Object getOneBooking(UUID id) {
        Optional<BookingModel> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            return optionalBooking.get();
        }

        return "O registro não foi encontrado.";
    }

    public Object updateBooking(BookingDto bookingDto, UUID id) {
        Optional<BookingModel> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            BookingModel bookingModel = new BookingModel(bookingDto, id);
            ClientModel clientModel = clientRepository.findById(bookingDto.id_client_bo()).get();
            bookingModel.setClientModel(clientModel);

            return bookingRepository.save(bookingModel);
        }

        return "O registro escolhido não existe.";
    }

    public boolean deleteBooking(UUID id) {
        Optional<BookingModel> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            bookingRepository.delete(optionalBooking.get());

            return true;
        }

        return false;
    }
}
