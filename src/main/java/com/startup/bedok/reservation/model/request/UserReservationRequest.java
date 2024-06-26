package com.startup.bedok.reservation.model.request;

import java.time.LocalDate;
import java.util.UUID;

public record UserReservationRequest(UUID advertisementId, LocalDate dateFrom, LocalDate dateTo) { }
