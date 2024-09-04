package com.startup.bedok.reservation.repository;

import com.startup.bedok.reservation.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByUserId(UUID userId);

    List<Reservation> findAllByAdvertisementId(UUID advertisementId);

    @Query(value = "update Reservation r set reservation_status = 3 where (reservation_status != 3 or reservation_status != 2) and r.update_date < ?1",
    nativeQuery = true)
    List<Reservation> findAllByStatusAndUpdateTime(Long updateTimePlusOneDay);

}
