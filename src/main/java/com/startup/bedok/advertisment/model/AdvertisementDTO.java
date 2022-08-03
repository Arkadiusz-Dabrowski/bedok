package com.startup.bedok.advertisment.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class AdvertisementDTO {

    @NotNull
    private Long hostId;
    @NotNull
    private String postCode;
    @NotNull
    private String hostStreet;
    @NotNull
    private List<MultipartFile> roomPhotos;

    private String roomDescription;
    @NotNull
    private String roomArea;
    @NotNull
    private String numBeds;
    @NotNull
    private List<PriceDTO> priceList;
    @NotNull
    private boolean sharedBeds;
    @NotNull
    private String language;
    private List<RoomEquipment> roomEquipment;
    private List<SharedEquipment> sharedEquipment;
    @NotNull
    private List<PaymentType> paymentType;
    private List<RentalRules> RentalRules;
}
