package com.startup.bedok.advertisment.model.response;

import com.startup.bedok.advertisment.model.enumerated.DistrictEnum;
import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.startup.bedok.guest.model.response.GuestResponse;
import com.startup.bedok.user.model.UserResponse;
import com.sun.istack.NotNull;
import lombok.Data;
import org.bson.types.Binary;

import java.util.List;


@Data
public class AdvertisementShort {
    @NotNull
    private final String title;
    @NotNull
    private final String city;
    @NotNull
    private final String district;
    @NotNull
    private final Integer numBeds;
    private final String description;
    private final RoomGender roomGender;
    private final List<GuestResponse> guests;
    private final Double price;
    private final String street;
    @NotNull
    private final Double roomArea;
    @NotNull
    private final UserResponse userResponse;
    @NotNull
    private final List<Binary> mainPhoto;
    @NotNull
    private final Integer usedBeds;
    private final boolean ironRoom;
    private final boolean hooverRoom;
    private final boolean televisionRoom;
    private final boolean radioRoom;
    private final boolean balconyRoom;
    private final boolean ironShared;
    private final boolean hooverShared;
    private final boolean televisionShared;
    private final boolean radioShared;
    private final boolean balconyShared;
    private final boolean active;
}
