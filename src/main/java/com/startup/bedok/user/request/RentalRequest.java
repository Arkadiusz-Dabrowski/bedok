package com.startup.bedok.user.request;

import com.startup.bedok.reservation.model.entity.Reservation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class RentalRequest {

    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate dateOfReservation;
    @OneToOne
    private Reservation reservation;
}
