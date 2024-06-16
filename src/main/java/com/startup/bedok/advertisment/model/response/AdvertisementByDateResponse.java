package com.startup.bedok.advertisment.model.response;

import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.startup.bedok.user.model.UserResponse;
import com.sun.istack.NotNull;
import org.bson.types.Binary;

import java.util.List;
import java.util.UUID;


public record AdvertisementByDateResponse(@NotNull UUID advertisementId, @NotNull UUID hostId, @NotNull String title, @NotNull String city, @NotNull String district,
                                          @NotNull Integer numBeds, String description, RoomGender roomGender,
                                          Double price, String street, @NotNull int numOfRooms,
                                          @NotNull UserResponse userResponse, @NotNull List<Binary> mainPhotos) {
}
