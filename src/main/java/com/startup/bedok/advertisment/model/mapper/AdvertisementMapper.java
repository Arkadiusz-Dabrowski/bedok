package com.startup.bedok.advertisment.model.mapper;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.Price;
import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
import com.startup.bedok.advertisment.model.request.AdvertisementShort;
import com.startup.bedok.advertisment.model.response.AdvertisementDTO;
import com.startup.bedok.host.model.HostResponse;
import com.startup.bedok.host.service.HostService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdvertisementMapper {

    private final HostService hostService;

    public AdvertisementDTO mapAdvertisementToAdvertisementDTO(Advertisement advertisement, List<Binary> photos) {
        return new AdvertisementDTO(
                hostService.getHostByID(advertisement.getHostId()),
                advertisement.getPostCode(),
                advertisement.getStreetName(),
                photos,
                advertisement.getRoomDescription(),
                advertisement.getRoomArea(),
                advertisement.getNumBeds(),
                advertisement.getUsedBeds(),
                advertisement.getPriceList().stream()
                        .map(PriceMapper::mapPricetoPriceDTO)
                        .collect(Collectors.toList()),
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
                Arrays.asList(advertisement.getRentalRulesObject().split(","))
        );
    }

    public AdvertisementShort mapAdvertisementToAdvertisementShort(Advertisement advertisement, Binary photo, HostResponse hostResponse) {
        return new AdvertisementShort(
                advertisement.getTitle(),
                advertisement.getDistrict(),
                advertisement.getNumBeds(),
                advertisement.getRoomDescription(),
                advertisement.getGenderRoomEnum(),
                Arrays.stream(advertisement.getGuests().split(",")).toList(),
                advertisement.getPriceList().stream()
                        .map(PriceMapper::mapPricetoPriceDTO)
                        .collect(Collectors.toList()),
                advertisement.getStreetName(),
                advertisement.getRoomArea(),
                hostResponse,
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
                advertisement.isBalconyShared()
        );
    }


    public Advertisement mapAdvertisementDTOToAdvertisement(AdvertisementRequest advertisementRequest,
                                                            List<Price> priceList) {
        return new Advertisement(
                advertisementRequest.getHostId(),
                advertisementRequest.getTitle(),
                advertisementRequest.getDistrict(),
                advertisementRequest.getGenderRoom(),
                advertisementRequest.getGuests(),
                advertisementRequest.getPostCode(),
                advertisementRequest.getStreetName(),
                null,
                advertisementRequest.getRoomDescription(),
                advertisementRequest.getRoomArea(),
                advertisementRequest.getNumBeds(),
                advertisementRequest.getUsedBeds(),
                priceList,
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
        advertisement.setGenderRoomEnum(request.getGenderRoom());
        advertisement.setGuests(String.join(",", request.getGuests()));
        advertisement.setPostCode(request.getPostCode());
        advertisement.setStreetName(request.getStreetName());
        advertisement.setRoomDescription(request.getRoomDescription());
        advertisement.setRoomArea(request.getRoomArea());
        advertisement.setNumBeds(request.getNumBeds());
        advertisement.setUsedBeds(request.getUsedBeds());
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
}
