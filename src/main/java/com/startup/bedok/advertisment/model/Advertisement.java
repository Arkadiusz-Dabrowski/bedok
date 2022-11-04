package com.startup.bedok.advertisment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Advertisement {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID hostId;
    private String postCode;
    private String hostStreet;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomPhoto> roomPhotos;
    private String roomDescription;
    private Double roomArea;
    private int numBeds;

    private int usedBeds;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(min = 1, max = 5)
    private List<Price> priceList;
    private Boolean sharedBeds;
    private String language;

    @ManyToMany
    @JoinTable(name = "advertisement_room_equipment",
            joinColumns = @JoinColumn(name = "advertisement_id"),
            inverseJoinColumns = @JoinColumn(name = "environment_id"))
    private List<Equipment> roomEquipment;

    @ManyToMany
    @JoinTable(name = "advertisement_shared_equipment",
            joinColumns = @JoinColumn(name = "advertisement_id"),
            inverseJoinColumns = @JoinColumn(name = "environment_id"))
    private List<Equipment> sharedEquipment;

    @ManyToMany
    @JoinTable(name = "advertisement_payment_type",
            joinColumns = @JoinColumn(name = "advertisement_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_type_id"))
    private List<PaymentType> paymentType;

    private String rentalRulesObject;

    public Advertisement(UUID hostId,
                         String postCode,
                         String hostStreet,
                         List<RoomPhoto> roomPhotos,
                         String roomDescription,
                         Double roomArea,
                         Integer numBeds,
                         Integer usedBeds,
                         List<Price> priceList,
                         Boolean sharedBeds,
                         String language,
                         List<Equipment> roomEquipment,
                         List<Equipment> sharedEquipment,
                         List<PaymentType> paymentType,
                         String rentalRulesObject) {
        this.hostId = hostId;
        this.postCode = postCode;
        this.hostStreet = hostStreet;
        this.roomPhotos = roomPhotos;
        this.roomDescription = roomDescription;
        this.roomArea = roomArea;
        this.numBeds = numBeds;
        this.usedBeds = usedBeds;
        this.priceList = priceList;
        this.sharedBeds = sharedBeds;
        this.language = language;
        this.roomEquipment = roomEquipment;
        this.sharedEquipment = sharedEquipment;
        this.paymentType = paymentType;
        this.rentalRulesObject = rentalRulesObject;
    }
}
