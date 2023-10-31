package com.spring.animal.hotel.spring.models;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel extends RepresentationModel<ClientModel> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cl;
    private String name_cl;
    private String phone_cl;
}
