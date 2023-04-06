package com.startup.bedok.guest.model.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class GuestResponse {
    private final UUID id;
    private final String name;
    private final Integer age;
    private final String language;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
}
