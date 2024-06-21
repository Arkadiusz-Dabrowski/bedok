package com.startup.bedok.advertisment.model.response;

import java.util.UUID;

public record AdvertisementFromGroupResponse(UUID hostId, String title, int bedsNumber,String city, UUID advertisementID) {
}
