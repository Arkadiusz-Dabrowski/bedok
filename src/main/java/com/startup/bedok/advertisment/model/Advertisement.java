package com.startup.bedok.advertisment.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Advertisement {

    @Id
    @GeneratedValue
    private Long id;
    private Long hostId;
    private String postCode;
    private String hostStreet;
    @OneToMany
    private List<RoomPhoto> roomPhotosUrl;
    private String roomDescription;
    private String roomArea;
    private String numBeds;
    @OneToMany
    private List<Price> priceList;
    private boolean sharedBeds;
    private String language;
    @ElementCollection(targetClass = RoomEquipment.class)
    @JoinTable(name = "advertisement", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "room", nullable = false)
    @Enumerated(EnumType.STRING)    private List<RoomEquipment> roomEquipment;
    @ElementCollection(targetClass = SharedEquipment.class)
    @JoinTable(name = "advertisement", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "shared", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<SharedEquipment> sharedEquipment;
    @ElementCollection(targetClass = PaymentType.class)
    @JoinTable(name = "advertisement", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "paymentType", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<PaymentType> paymentType;
    @ElementCollection(targetClass = RentalRules.class)
    @JoinTable(name = "advertisement", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "rules", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<RentalRules> rentalRulesObject;

    public Advertisement(Long hostId,
                         String postCode,
                         String hostStreet,
                         List<String> roomPhotos,
                         String roomDescription,
                         String roomArea,
                         String numBeds,
                         List<Price> priceList,
                         boolean sharedBeds,
                         String language,
                         List<RoomEquipment> roomEquipment,
                         List<SharedEquipment> sharedEquipment,
                         List<PaymentType> paymentType,
                         List<RentalRules> rentalRulesObject) {
        this.hostId = hostId;
        this.postCode = postCode;
        this.hostStreet = hostStreet;
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
        this.rentalRulesObject = rentalRulesObject;
    }
}
