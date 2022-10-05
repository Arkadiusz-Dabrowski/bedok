package com.startup.bedok.advertisment.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class AdvertisementRequest {

    @NotNull
    private UUID hostId;
    @NotNull
    private String postCode;
    @NotNull
    private String hostStreet;
    @NotNull
    private List<MultipartFile> roomPhotos;

    private String roomDescription;

    private Double roomArea;
    @NotNull
    private Integer numBeds;
    @NotNull
    private Integer usedBeds;
    @NotNull
    private List<PriceDTO> priceList;
    @NotNull
    private Boolean sharedBeds;
    @NotNull
    private String language;

    private List<String> roomEquipment;
    private List<String> sharedEquipment;
    private List<String> paymentType;
    private List<String> RentalRules;
}
