package com.startup.bedok.advertisment.model.request;

import com.startup.bedok.advertisment.model.enumerated.DistrictEnum;
import com.startup.bedok.advertisment.model.enumerated.GenderRoomEnum;
import com.startup.bedok.advertisment.model.response.PriceDTO;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class AdvertisementRequest {
    @NotNull
    private String title;
    @NotNull
    private DistrictEnum district;
    @NotNull
    private GenderRoomEnum gennderRoom;
    private List<String> guests;
    @NotNull
    private UUID hostId;
    @NotNull
    private String postCode;
    @NotNull
    private String hostStreet;
    private String roomShortDescription;
    private String roomDescription;
    @NotNull
    private Double roomArea;
    @NotNull
    private Integer numBeds;
    @NotNull
    private Integer usedBeds;
    @NotNull
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
    private List<String> rentalRules;
}
