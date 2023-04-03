package com.startup.bedok.guest.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GuestRequest {
    private final String name;
    private final Integer age;
    private final String language;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
}
