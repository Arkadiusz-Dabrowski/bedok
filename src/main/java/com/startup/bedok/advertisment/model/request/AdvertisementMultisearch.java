package com.startup.bedok.advertisment.model.request;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AdvertisementMultisearch {

    private String street;
    private Double roomAreaFrom;
    private Double roomAreaTo;
    private List<String> language;

    private List<String> roomEquipment;
    private List<String> sharedEquipment;
    private List<String> paymentType;
    private List<String> rentalRules;
}
