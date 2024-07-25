package com.startup.bedok.reservation.model.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserReservationRequest(UUID advertisementId, LocalDate dateFrom, LocalDate dateTo) { }
