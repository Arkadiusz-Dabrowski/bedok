package com.startup.bedok.advertisment.model.entity;

import com.startup.bedok.advertisment.model.enumerated.DistrictEnum;
import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.startup.bedok.guest.model.entity.Guest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Advertisement {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID hostId;
    private String city;
    private String title;
    @OneToOne
    private District district;
    @Enumerated
    private RoomGender roomGender;
    @ManyToMany
    private Set<Guest> guests;
    private String postCode;
    private String streetName;
    private String roomDescription;
    private double roomArea;
    private int numBeds;
    private int usedBeds;
    private double price;
    private double firstStageDiscount;
    private double secondStageDiscount;
    private double thirdStageDiscount;
    private double fourthStageDiscount;
    private Boolean sharedBeds;
    private String language;
    private boolean ironRoom;
    private boolean hooverRoom;
    private boolean televisionRoom;
    private boolean radioRoom;
    private boolean balconyRoom;
    private boolean ironShared;
    private boolean hooverShared;
    private boolean televisionShared;
    private boolean radioShared;
    private boolean balconyShared;
    private boolean cache;
    private boolean transfer;
    private String rentalRulesObject;

    private Long uploadDate;
    private Long updateDate;
    private boolean active;


    public Advertisement(UUID hostId,
                         String title,
                         String city,
                         District district,
                         RoomGender roomGender,
                         Set<String> guests,
                         String postCode,
                         String streetName,
                         String roomDescription,
                         double roomArea,
                         int numBeds,
                         int usedBeds,
                         double price,
                         double firstStageDiscount,
                         double secondStageDiscount,
                         double thirdStageDiscount,
                         double fourthStageDiscount,
                         Boolean sharedBeds,
                         String language,
                         boolean ironRoom,
                         boolean hooverRoom,
                         boolean televisionRoom,
                         boolean radioRoom,
                         boolean balconyRoom,
                         boolean ironShared,
                         boolean hooverShared,
                         boolean televisionShared,
                         boolean radioShared,
                         boolean balconyShared,
                         boolean cache,
                         boolean transfer,
                         String rentalRulesObject) {
        this.hostId = hostId;
        this.title = title;
        this.city = city;
        this.district = district;
        this.roomGender = roomGender;
        this.postCode = postCode;
        this.streetName = streetName;
        this.roomDescription = roomDescription;
        this.roomArea = roomArea;
        this.numBeds = numBeds;
        this.usedBeds = usedBeds;
        this.price = price;
        this.firstStageDiscount = firstStageDiscount;
        this.secondStageDiscount = secondStageDiscount;
        this.thirdStageDiscount = thirdStageDiscount;
        this.fourthStageDiscount = fourthStageDiscount;
        this.sharedBeds = sharedBeds;
        this.language = language;
        this.ironRoom = ironRoom;
        this.hooverRoom = hooverRoom;
        this.televisionRoom = televisionRoom;
        this.radioRoom = radioRoom;
        this.balconyRoom = balconyRoom;
        this.ironShared = ironShared;
        this.hooverShared = hooverShared;
        this.televisionShared = televisionShared;
        this.radioShared = radioShared;
        this.balconyShared = balconyShared;
        this.cache = cache;
        this.transfer = transfer;
        this.rentalRulesObject = rentalRulesObject;
        this.uploadDate = Instant.now().toEpochMilli();
        this.updateDate = Instant.now().toEpochMilli();
        this.active = true;
    }
}
