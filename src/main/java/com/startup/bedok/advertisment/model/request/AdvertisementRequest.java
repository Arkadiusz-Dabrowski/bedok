package com.startup.bedok.advertisment.model.request;

import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class AdvertisementRequest {
    @NotBlank(message = "field title, can't be empty")
    private String title;
    @NotBlank(message = "field district, can't be empty")
    private String district;
    @NotBlank(message = "field city, can't be empty")
    private String city;
    private RoomGender roomGender;
    @NotBlank(message = "field postCode, can't be empty")
    private String postCode;
    @NotBlank(message = "field streetName, can't be empty")
    private String streetName;
    private String roomShortDescription;
    private String roomDescription;
    @NotNull(message = "field roomArea, can't be empty")
    private Double roomArea;
    @NotNull(message = "field numBeds, can't be empty")
    private Integer numBeds;
    @NotNull(message = "field dailyPrice, can't be empty")
    private Double dailyPrice;
    private Double weeklyPrice;
    private Double monthlyPrice;
    private String language;
    private boolean ironRoom;
    private boolean hooverRoom;
    private boolean televisionRoom;
    private boolean radioRoom;
    private boolean balconyRoom;
    private boolean bathroom;
    private boolean ironShared;
    private boolean hooverShared;
    private boolean televisionShared;
    private boolean radioShared;
    private boolean balconyShared;
    private boolean cache;
    private boolean transfer;
    private List<String> rentalRules;
}
