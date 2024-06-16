package com.startup.bedok.advertisment.model.request;

import com.startup.bedok.advertisment.validator.ValidDateRange;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@ValidDateRange
public class AdvertisementMultisearch {

    private String location;
    private Double roomAreaFrom;
    private Double roomAreaTo;
    private List<String> language;
    private int numberOfClients;

    private List<String> roomEquipment;
    private List<String> sharedEquipment;
    private List<String> paymentType;
    private List<String> rentalRules;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private boolean active = true;
    private int pageNumber = 0;
    private int pageSize = 10;
}
