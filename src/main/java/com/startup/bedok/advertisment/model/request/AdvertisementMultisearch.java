package com.startup.bedok.advertisment.model.request;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class AdvertisementMultisearch {

    private String location;
    private Double roomAreaFrom;
    private Double roomAreaTo;
    private List<String> language;

    private List<String> roomEquipment;
    private List<String> sharedEquipment;
    private List<String> paymentType;
    private List<String> rentalRules;
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction direction = Sort.DEFAULT_DIRECTION;
    private String sortBy = "uploadDate";
}
