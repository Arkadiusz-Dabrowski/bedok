package com.startup.bedok.reservation.repository;

import com.startup.bedok.reservation.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
}
