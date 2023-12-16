package com.spring.animal.hotel.spring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cl")
    private int id;
    private String name_cl;
    private String phone_cl;

    public ClientModel(ClientDto clientDto, int id) {
        if (id != -1) {
            this.id = id;
        }
        this.name_cl = clientDto.name_cl();
        this.phone_cl = clientDto.phone_cl();
    }
}
