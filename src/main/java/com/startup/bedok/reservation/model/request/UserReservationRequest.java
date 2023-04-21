package com.startup.bedok.reservation.model.request;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

public record UserReservationRequest(UUID userId, UUID advertisementId, LocalDate dateFrom, LocalDate dateTo) { }
