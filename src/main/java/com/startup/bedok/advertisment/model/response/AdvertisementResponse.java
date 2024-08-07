package com.startup.bedok.advertisment.model.response;

import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.startup.bedok.guest.model.response.GuestResponse;
import com.startup.bedok.user.model.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class AdvertisementResponse {

    private UserResponse host;
    private UUID advertisementId;
    private String title;
    private String city;
    private String district;
    private String postCode;
    private String streetName;
    private List<String> roomPhotos;

    private String roomDescription;
    private RoomGender roomGender;
    private List<GuestResponse> guestsList;
    private Double roomArea;
    private Integer numBeds;
    private Double price;
    private String language;
    private boolean ironRoom;
    private boolean hooverRoom;
    private boolean televisionRoom;
    private boolean radioRoom;
    private boolean balconyRoom;
    private boolean bathroom;
    private boolean ironShared;
    private boolean hooverShared;
    private boolean televisionShared;
    private boolean radioShared;
    private boolean balconyShared;
    private boolean cache;
    private boolean transfer;
    private List<String> RentalRules;
    private boolean active;
}
