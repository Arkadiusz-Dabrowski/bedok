package com.startup.bedok.advertisment.model.request;

import java.time.LocalDate;

public record AdvertisementGroupSearch(LocalDate dateFrom, LocalDate dateTo, int numberOfBeds){ }
