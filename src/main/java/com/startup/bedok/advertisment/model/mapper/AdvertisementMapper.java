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
                advertisementRequest.getGennderRoom(),
                advertisementRequest.getGuests(),
                advertisementRequest.getPostCode(),
                advertisementRequest.getHostStreet(),
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

    private static String addRentalRules(List<String> rentalRules){
        if(!rentalRules.isEmpty())
        return String.join(",", rentalRules);

        return "";
    }
}
