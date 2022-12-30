package com.startup.bedok.advertisment.model.response;

import com.startup.bedok.host.model.HostResponse;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class AdvertisementDTO {

    @NotNull
    private HostResponse host;
    @NotNull
    private String postCode;
    @NotNull
    private String hostStreet;
    @NotNull
    private List<Binary> roomPhotos;

    private String roomDescription;
    @NotNull
    private Double roomArea;
    @NotNull
    private Integer numBeds;
    @NotNull
    private Integer usedBeds;
    private List<PriceDTO> priceList;
    @NotNull
    private Boolean sharedBeds;
    @NotNull
    private String language;
    private boolean ironRoom;
    private boolean hooverRoom;
    private boolean televisionRoom;
    private boolean radioRoom;
    private boolean balconyRoom;
    private boolean ironShared;
    private boolean hooverShared;
    private boolean televisionShared;
    private boolean radioShared;
    private boolean balconyShared;
    private boolean cache;
    private boolean transfer;
    private List<String> RentalRules;
}
