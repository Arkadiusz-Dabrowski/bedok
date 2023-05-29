package com.startup.bedok.reservation.model.response;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationDTO(LocalDate dateFrom, LocalDate dateTo, UUID advertisementId, boolean accepted, boolean paid) {
}
