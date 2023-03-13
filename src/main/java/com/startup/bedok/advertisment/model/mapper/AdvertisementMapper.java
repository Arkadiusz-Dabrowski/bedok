package com.startup.bedok.advertisment.model.mapper;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
import com.startup.bedok.advertisment.model.request.AdvertisementShort;
import com.startup.bedok.advertisment.model.response.AdvertisementDTO;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdvertisementMapper {

    private final UserService userService;

    public AdvertisementDTO mapAdvertisementToAdvertisementDTO(Advertisement advertisement, List<Binary> photos) {
        return new AdvertisementDTO(
                userService.getUserByID(advertisement.getHostId()),
                advertisement.getTitle(),
                advertisement.getCity(),
                advertisement.getPostCode(),
                advertisement.getStreetName(),
                photos,
                advertisement.getRoomDescription(),
                advertisement.getRoomArea(),
                advertisement.getNumBeds(),
                advertisement.getUsedBeds(),
                advertisement.getPrice(),
                advertisement.getSharedBeds(),
                advertisement.getLanguage(),
                advertisement.isIronRoom(),
                advertisement.isHooverRoom(),
                advertisement.isTelevisionRoom(),
                advertisement.isRadioRoom(),
                advertisement.isBalconyRoom(),
                advertisement.isIronShared(),
                advertisement.isHooverShared(),
                advertisement.isTelevisionShared(),
                advertisement.isRadioShared(),
                advertisement.isBalconyShared(),
                advertisement.isCache(),
                advertisement.isTransfer(),
                Arrays.asList(advertisement.getRentalRulesObject().split(",")),
                advertisement.isActive()
        );
    }

    public AdvertisementShort mapAdvertisementToAdvertisementShort(Advertisement advertisement, List<Binary> photo, UserResponse userResponse) {
        return new AdvertisementShort(
                advertisement.getTitle(),
                advertisement.getCity(),
                advertisement.getDistrict(),
                advertisement.getNumBeds(),
                advertisement.getRoomDescription(),
                advertisement.getRoomGender(),
                Arrays.stream(advertisement.getGuests().split(","))
                        .toList(),
                advertisement.getPrice(),
                advertisement.getStreetName(),
                advertisement.getRoomArea(),
                userResponse,
                photo,
                advertisement.getUsedBeds(),
                advertisement.isIronRoom(),
                advertisement.isHooverRoom(),
                advertisement.isTelevisionRoom(),
                advertisement.isRadioRoom(),
                advertisement.isBalconyRoom(),
                advertisement.isIronShared(),
                advertisement.isHooverShared(),
                advertisement.isTelevisionShared(),
                advertisement.isRadioShared(),
                advertisement.isBalconyShared(),
                advertisement.isActive()
        );
    }


    public Advertisement mapAdvertisementRequestToAdvertisement(AdvertisementRequest advertisementRequest) {
        return new Advertisement(
                advertisementRequest.getHostId(),
                advertisementRequest.getTitle(),
                advertisementRequest.getCity(),
                advertisementRequest.getDistrict(),
                advertisementRequest.getGenderRoom(),
                advertisementRequest.getGuests(),
                advertisementRequest.getPostCode(),
                advertisementRequest.getStreetName(),
                advertisementRequest.getRoomDescription(),
                advertisementRequest.getRoomArea(),
                advertisementRequest.getNumBeds(),
                advertisementRequest.getUsedBeds(),
                advertisementRequest.getPrice(),
                advertisementRequest.getFirstStageDiscount(),
                advertisementRequest.getSecondStageDiscount(),
                advertisementRequest.getThirdStageDiscount(),
                advertisementRequest.getFourthStageDiscount(),
                advertisementRequest.getSharedBeds(),
                advertisementRequest.getLanguage(),
                advertisementRequest.isIronRoom(),
                advertisementRequest.isHooverRoom(),
                advertisementRequest.isTelevisionRoom(),
                advertisementRequest.isRadioRoom(),
                advertisementRequest.isBalconyRoom(),
                advertisementRequest.isIronShared(),
                advertisementRequest.isHooverShared(),
                advertisementRequest.isTelevisionShared(),
                advertisementRequest.isRadioShared(),
                advertisementRequest.isBalconyShared(),
                advertisementRequest.isCache(),
                advertisementRequest.isTransfer(),
                addRentalRules(advertisementRequest.getRentalRules())
        );
    }

    public Advertisement updateAdvertisementFromRequest(Advertisement advertisement,
                                                        AdvertisementRequest request) {
        advertisement.setTitle(request.getTitle());
        advertisement.setDistrict(request.getDistrict());
        advertisement.setRoomGender(request.getGenderRoom());
        advertisement.setGuests(String.join(",", request.getGuests()));
        advertisement.setPostCode(request.getPostCode());
        advertisement.setStreetName(request.getStreetName());
        advertisement.setRoomDescription(request.getRoomDescription());
        advertisement.setRoomArea(request.getRoomArea());
        advertisement.setNumBeds(request.getNumBeds());
        advertisement.setUsedBeds(request.getUsedBeds());
        advertisement.setPrice(request.getPrice());
        advertisement.setFirstStageDiscount(request.getFirstStageDiscount());
        advertisement.setSecondStageDiscount(request.getSecondStageDiscount());
        advertisement.setThirdStageDiscount(request.getThirdStageDiscount());
        advertisement.setFourthStageDiscount(request.getFourthStageDiscount());
        advertisement.setSharedBeds(request.getSharedBeds());
        advertisement.setLanguage(request.getLanguage());
        advertisement.setIronRoom(request.isIronRoom());
        advertisement.setHooverRoom(request.isHooverRoom());
        advertisement.setTelevisionRoom(request.isTelevisionRoom());
        advertisement.setRadioRoom(request.isRadioRoom());
        advertisement.setBalconyRoom(request.isBalconyRoom());
        advertisement.setIronShared(request.isIronShared());
        advertisement.setHooverShared(request.isHooverShared());
        advertisement.setTelevisionShared(request.isTelevisionShared());
        advertisement.setRadioShared(request.isRadioShared());
        advertisement.setBalconyShared(request.isBalconyShared());
        advertisement.setCache(request.isCache());
        advertisement.setTransfer(request.isTransfer());
        advertisement.setRentalRulesObject(addRentalRules(request.getRentalRules()));
        advertisement.setUpdateDate(Instant.now().getEpochSecond());
        return advertisement;
    }

    private static String addRentalRules(List<String> rentalRules){
        if(!rentalRules.isEmpty())
        return String.join(",", rentalRules);

        return "";
    }

    private Integer calculatePrice(Advertisement advertisement){
        return null;
    }
}
