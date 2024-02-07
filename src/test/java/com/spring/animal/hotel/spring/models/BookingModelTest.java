package com.spring.animal.hotel.spring.models;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookingModelTest {
    @Test
    @DisplayName("Deve construir o objeto BookingModel corretamente a partir de um BookingDto.")
    void testBookingModelConstructor() {
        BookingDto bookingDto = new BookingDto(new Timestamp(1705953600),
                                                new Timestamp(1706953600),
                                                1000,
                                                1);

        BookingModel booking = new BookingModel(bookingDto, UUID.nameUUIDFromBytes(new byte[1]));

        assertNotNull(booking.getId_bo());
        assertEquals(booking.getDate_check_in_bo(), new Timestamp(1705953600));
        assertEquals(booking.getDate_check_out_bo(), new Timestamp(1706953600));
        assertEquals(booking.getCost_bo(), 1000);
    }
}
