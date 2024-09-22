package com.startup.bedok.advertisment.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class AdvertisementGroupCriteriaResponse {
    private UUID advertisementGroupId;
    private long availableBeds;
    private List<AdvertisementShort> advertisements = new ArrayList<>();

    public AdvertisementGroupCriteriaResponse(UUID advertisementGroup, long availableBeds) {
        this.advertisementGroupId = advertisementGroup;
        this.availableBeds = availableBeds;
    }

    public void addAdvertisementToList(AdvertisementShort advertisement){
        advertisements.add(advertisement);
    }

}
