package com.startup.bedok.advertisment.model.request;

import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class AdvertisementRequest {
    @NotNull
    private String title;
    @NotNull
    private String district;
    @NotNull
    private String city;
    @NotNull
    private RoomGender genderRoom;
    private List<String> guests;
    @NotNull
    private String postCode;
    @NotNull
    private String streetName;
    private String roomShortDescription;
    private String roomDescription;
    @NotNull
    private Double roomArea;
    @NotNull
    private Integer numBeds;
    @NotNull
    private Integer usedBeds;
    @NotNull
    private Integer price;
    @NotNull
    private Double firstStageDiscount;
    @NotNull
    private Double secondStageDiscount;
    @NotNull
    private Double thirdStageDiscount;
    @NotNull
    private Double fourthStageDiscount;
    @NotNull
    private Boolean sharedBeds;
    @NotNull
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
    private List<String> rentalRules;
}
