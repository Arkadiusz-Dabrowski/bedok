package com.startup.bedok.advertisment.model;

import com.startup.bedok.helper.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue
    private Long id;
    private Long hostId;
    private String postCode;
    private String hostStreet;
    private int roomId;
    @OneToMany
    private List<RoomPhoto> roomPhotosUrl;
    private String roomDescription;
    private String roomArea;
    private String numBeds;
    @OneToMany
    private List<Price> priceList;
    private boolean sharedBeds;
    private String language;
    @OneToMany
    private List<RoomEquipment> roomEquipment;
    @OneToMany
    private List<SharedEquipment> sharedEquipment;
    @ManyToMany
    private List<PaymentType> paymentType;
    @OneToMany
    private List<RentalRules> RentalRulesObject;

    public Advertisement(Long hostId, String postCode, String hostStreet, int roomId, List<RoomPhoto> roomPhotos, String roomDescription, String roomArea, String numBeds, List<Price> priceList, boolean sharedBeds, String language, List<RoomEquipment> roomEquipment, List<SharedEquipment> sharedEquipment, List<PaymentType> paymentType, List<RentalRules> rentalRulesObject) {
        this.hostId = hostId;
        this.postCode = postCode;
        this.hostStreet = hostStreet;
        this.roomId = roomId;
        this.roomPhotosUrl = roomPhotosUrl;
        this.roomDescription = roomDescription;
        this.roomArea = roomArea;
        this.numBeds = numBeds;
        this.priceList = priceList;
        this.sharedBeds = sharedBeds;
        this.language = language;
        this.roomEquipment = roomEquipment;
        this.sharedEquipment = sharedEquipment;
        this.paymentType = paymentType;
        RentalRulesObject = rentalRulesObject;
    }
}
