package com.startup.bedok.advertisment.model.entity;

import com.startup.bedok.advertisment.model.enumerated.DistrictEnum;
import com.startup.bedok.advertisment.model.enumerated.GenderRoomEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    private UUID hostId;
    private String title;
    @Enumerated
    private DistrictEnum district;
    @Enumerated
    private GenderRoomEnum genderRoomEnum;
    private String guests;
    private String postCode;
    private String streetName;
    @OneToMany(orphanRemoval = true)
    private List<RoomPhoto> roomPhotos;
    private String roomDescription;
    private Double roomArea;
    private int numBeds;

    private int usedBeds;
    @OneToMany(orphanRemoval = true)
    @Size(min = 1, max = 5)
    private List<Price> priceList;
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


    public Advertisement(UUID hostId,
                         String title,
                         DistrictEnum district,
                         GenderRoomEnum genderRoomEnum,
                         List<String> guests,
                         String postCode,
                         String streetName,
                         List<RoomPhoto> roomPhotos,
                         String roomDescription,
                         Double roomArea,
                         int numBeds,
                         int usedBeds,
                         List<Price> priceList,
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
        this.district = district;
        this.genderRoomEnum = genderRoomEnum;
        this.guests = String.join(",", guests);
        this.postCode = postCode;
        this.streetName = streetName;
        this.roomPhotos = roomPhotos;
        this.roomDescription = roomDescription;
        this.roomArea = roomArea;
        this.numBeds = numBeds;
        this.usedBeds = usedBeds;
        this.priceList = priceList;
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
    }

    public Advertisement(UUID hostId,
                         String title,
                         DistrictEnum district,
                         GenderRoomEnum genderRoomEnum,
                         List<String> guests,
                         String postCode,
                         String streetName,
                         List<RoomPhoto> roomPhotos,
                         String roomDescription,
                         Double roomArea,
                         int numBeds,
                         int usedBeds,
                         List<Price> priceList,
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
                         String rentalRulesObject,
                         Long updateDate) {
        this.hostId = hostId;
        this.title = title;
        this.district = district;
        this.genderRoomEnum = genderRoomEnum;
        this.guests = String.join(",", guests);
        this.postCode = postCode;
        this.streetName = streetName;
        this.roomPhotos = roomPhotos;
        this.roomDescription = roomDescription;
        this.roomArea = roomArea;
        this.numBeds = numBeds;
        this.usedBeds = usedBeds;
        this.priceList = priceList;
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
        this.updateDate = updateDate;
    }
}
