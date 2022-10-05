package com.startup.bedok.advertisment.model;

import com.startup.bedok.host.model.HostResponse;
import com.sun.istack.NotNull;
import lombok.Data;
import org.bson.types.Binary;


@Data
public class AdvertisementShort {
    @NotNull
    private final HostResponse hostResponse;
    @NotNull
    private final String hostStreet;
    @NotNull
    private final Binary mainPhoto;
    @NotNull
    private final Double roomArea;
    @NotNull
    private final Integer numBeds;
    @NotNull
    private final Integer usedBeds;
    @NotNull
    private final String language;

}
