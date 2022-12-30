package com.startup.bedok.advertisment.model;


import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class AdvertisementPage {

    private final int pageNumber = 0;
    private final int pageSize = 10;
    private Sort.Direction direction = Sort.DEFAULT_DIRECTION;
    private final String sortBy = "roomArea";
}
