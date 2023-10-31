package com.spring.animal.hotel.spring.models;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_bo;
    private Timestamp date_check_in_bo;
    private Timestamp date_check_out_bo;
    private int cost_bo;

    @ManyToOne
    @JoinColumn(name = "id_client_bo")
    private ClientModel clientModel;
}
