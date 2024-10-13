package com.startup.bedok.reservation.model.response;

import com.startup.bedok.reservation.model.entity.ReservationStatus;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationDTO(LocalDate dateFrom, LocalDate dateTo, UUID advertisementId, ReservationStatus reservationStatus) {
}
