package com.startup.bedok.advertisment.model;

import com.startup.bedok.helper.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class AdvertisementDTO {

    private Long hostId;
    private String postCode;
    private String hostStreet;
    private int roomId;
    private List<MultipartFile> roomPhotos;
    private String roomDescription;
    private String roomArea;
    private String numBeds;
    private List<Price> priceList;
    private boolean sharedBeds;
    private String language;
    private List<RoomEquipment> roomEquipment;
    private List<SharedEquipment> sharedEquipment;
    private List<PaymentType> paymentType;
    private List<RentalRules> RentalRulesObject;


}
