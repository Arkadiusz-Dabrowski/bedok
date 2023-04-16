package com.startup.bedok.reservation.model.request;

import java.time.LocalDate;
import java.util.UUID;

public record AnonymousReservationRequest(String guestName, UUID advertisementId, String language, Integer age, LocalDate dateFrom, LocalDate dateTo) { }
