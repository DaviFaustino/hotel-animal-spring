package com.spring.animal.hotel.spring.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientModelTest {
    @Test
    @DisplayName("Deve construir o objeto ClientModel corretamente a partir de um ClientDto.")
    void testClientModelConstructor() {
        ClientDto clientDto = new ClientDto("Areovaldo",  "(55) 55555-5555");

        ClientModel client = new ClientModel(clientDto, -1);

        assertNotNull(client.getId());
        assertEquals(client.getName_cl(), "Areovaldo");
        assertEquals(client.getPhone_cl(), "(55) 55555-5555");
    }
}
 