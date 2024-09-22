package com.startup.bedok.advertisment.model.entity;

import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.startup.bedok.reservation.model.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "advertisement_group_id")
    private AdvertisementGroup advertisementGroup;
    private UUID hostId;
    @Column(nullable = false)
    private String city;
    @Column(unique = true)
    private String title;
    private String district;
    @Enumerated
    private RoomGender roomGender;
    private String postCode;
    private String streetName;
    private String roomDescription;
    private double roomArea;
    private int numBeds;
    @OneToMany(mappedBy = "advertisement")
    private List<Reservation> reservations;
    @Column(nullable = false)
    private Double dailyPrice;
    @Column(nullable = false)
    private Double weeklyPrice;
    @Column(nullable = false)
    private Double monthlyPrice;
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
    @Column(columnDefinition = "boolean default false")
    private boolean bathroom;
    private boolean transfer;
    private String rentalRulesObject;
    @Column(nullable = false)
    private Long uploadDate;
    @Column(nullable = false)
    private Long updateDate;
    @Column(nullable = false)
    private boolean active;


    public Advertisement(UUID hostId,
                         String title,
                         String city,
                         String district,
                         RoomGender roomGender,
                         String postCode,
                         String streetName,
                         String roomDescription,
                         double roomArea,
                         int numBeds,
                         Double dailyPrice,
                         Double weeklyPrice,
                         Double monthlyPrice,
                         String language,
                         boolean ironRoom,
                         boolean hooverRoom,
                         boolean televisionRoom,
                         boolean radioRoom,
                         boolean balconyRoom,
                         boolean bathroom,
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
        this.dailyPrice = dailyPrice;
        this.weeklyPrice = weeklyPrice;
        this.monthlyPrice = monthlyPrice;
        this.language = language;
        this.ironRoom = ironRoom;
        this.hooverRoom = hooverRoom;
        this.televisionRoom = televisionRoom;
        this.radioRoom = radioRoom;
        this.balconyRoom = balconyRoom;
        this.bathroom = bathroom;
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
