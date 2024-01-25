package com.spring.animal.hotel.spring.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import com.spring.animal.hotel.spring.models.BookingDto;
import com.spring.animal.hotel.spring.models.BookingModel;
import com.spring.animal.hotel.spring.models.ClientDto;
import com.spring.animal.hotel.spring.models.ClientModel;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class BookingRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Booking successfully from DB")
    void testFindByClientModelIdCase1() {
        ClientModel client = this.createClient();

        BookingDto data = new BookingDto(new Timestamp(1705953600), new Timestamp(1706953600), 2000, client.getId());
        this.createBooking(data, null, client);

        List<BookingModel> result = this.bookingRepository.findByClientModelId(1);

        assertThat(result.isEmpty()).isFalse();
    }

    private BookingModel createBooking(BookingDto data, UUID id, ClientModel client) {
        BookingModel newBooking = new BookingModel(data, id);
        newBooking.setClientModel(client);
        this.entityManager.persist(newBooking);
        return newBooking;
    }

    private ClientModel createClient() {
        ClientModel newClient = new ClientModel(new ClientDto("Darla", "(00) 000000"), -1);
        this.entityManager.persist(newClient);
        return newClient;
    }
}
