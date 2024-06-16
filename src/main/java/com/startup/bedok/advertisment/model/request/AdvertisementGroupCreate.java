package com.startup.bedok.advertisment.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdvertisementGroupCreate {
    String title;
    List<UUID> advertisementsId;
    String description;
    UUID hostId;
    String city;
}
