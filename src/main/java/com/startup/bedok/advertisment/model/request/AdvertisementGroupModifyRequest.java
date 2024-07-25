package com.startup.bedok.advertisment.model.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AdvertisementGroupModifyRequest {
    private UUID advertisementId;
    private UUID advertisementGroupId;
}
