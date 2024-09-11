package com.startup.bedok.reservation.repository;

import com.startup.bedok.reservation.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByUserId(UUID userId);

    List<Reservation> findAllByAdvertisementId(UUID advertisementId);

    @Query(value = "select r from Reservation r where (r.reservationStatus = 1 or r.reservationStatus = 4) and r.updateDate < ?1")
    List<Reservation> findAllByStatusAndUpdateTime(Long updateTimePlusOneDay);
}
