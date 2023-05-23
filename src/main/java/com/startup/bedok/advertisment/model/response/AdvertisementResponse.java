package com.startup.bedok.advertisment.model.response;

import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.startup.bedok.guest.model.response.GuestResponse;
import com.startup.bedok.user.model.UserResponse;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class AdvertisementResponse {

    @NotNull
    private UserResponse host;
    @NotNull
    private String title;
    @NotNull
    private String city;
    @NotNull
    private String district;
    @NotNull
    private String postCode;
    @NotNull
    private String hostStreet;
    @NotNull
    private List<Binary> roomPhotos;

    private String roomDescription;
    private RoomGender roomGender;
    private List<GuestResponse> guestsList;
    @NotNull
    private Double roomArea;
    @NotNull
    private Integer numBeds;
    @NotNull
    private Double price;
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
    private List<String> RentalRules;
    private boolean active;
}
