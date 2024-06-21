package com.startup.bedok.advertisment.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class AdvertisementGroupResponse {
    private String title;
    private String city;
    private UUID hostId;
    private int numOfBeds;
    private List<UUID> advertisementsId;
}
