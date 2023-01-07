package com.startup.bedok.advertisment.model.request;

import com.startup.bedok.advertisment.model.enumerated.DistrictEnum;
import com.startup.bedok.advertisment.model.enumerated.GenderRoomEnum;
import com.startup.bedok.advertisment.model.response.PriceDTO;
import com.startup.bedok.host.model.HostResponse;
import com.sun.istack.NotNull;
import lombok.Data;
import org.bson.types.Binary;

import java.util.List;


@Data
public class AdvertisementShort {
    @NotNull
    private final String title;
    @NotNull
    private final DistrictEnum district;
    @NotNull
    private final Integer numBeds;
    private final String descryption;
    private final GenderRoomEnum gennderRoom;
    private final List<String> guests;
    private final List<PriceDTO> priceDTO;
    private final String street;
    @NotNull
    private final Double roomArea;
    @NotNull
    private final HostResponse hostResponse;
    @NotNull
    private final Binary mainPhoto;
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
}
