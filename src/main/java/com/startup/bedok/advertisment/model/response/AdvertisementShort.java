package com.startup.bedok.advertisment.model.response;

import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.startup.bedok.guest.model.response.GuestResponse;
import com.startup.bedok.user.model.UserResponse;
import com.sun.istack.NotNull;
import org.bson.types.Binary;

import java.util.List;
import java.util.UUID;


public record AdvertisementShort(@NotNull UUID advertisementId, @NotNull UUID hostId, @NotNull String title, @NotNull String city, @NotNull String district,
                                 @NotNull Integer numBeds, String description, RoomGender roomGender,
                                 List<GuestResponse> guests, Double price, String street, @NotNull Double roomArea,
                                 @NotNull UserResponse userResponse, @NotNull List<Binary> mainPhotos,
                                 @NotNull boolean ironRoom, boolean hooverRoom, boolean televisionRoom,
                                 boolean radioRoom, boolean balconyRoom, boolean bathroom, boolean ironShared, boolean hooverShared,
                                 boolean televisionShared, boolean radioShared, boolean balconyShared, boolean active) {
}
