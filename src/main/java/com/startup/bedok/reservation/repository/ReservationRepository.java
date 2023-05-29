package com.startup.bedok.reservation.repository;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.reservation.model.entity.Reservation;
import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByUserId(UUID userId);

    List<Reservation> findAllByAdvertisementId(UUID advertisementId);
}
