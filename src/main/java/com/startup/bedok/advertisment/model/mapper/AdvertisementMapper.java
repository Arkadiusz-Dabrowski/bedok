package com.startup.bedok.advertisment.model.mapper;

import com.startup.bedok.advertisment.model.*;
import com.startup.bedok.advertisment.services.EquipmentService;
import com.startup.bedok.advertisment.services.PaymentTypeService;
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

    private final EquipmentService equipmentService;
    private final PaymentTypeService paymentTypeService;
    private final HostService hostService;

    public AdvertisementDTO mapAdvertisementToAdvertisementDTO(Advertisement advertisement, List<Binary> photos) {
        return new AdvertisementDTO(
                hostService.getHostByID(advertisement.getHostId()),
                advertisement.getPostCode(),
                advertisement.getHostStreet(),
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
                advertisement.getRoomEquipment().stream().map(Equipment::getCode).collect(Collectors.toList()),
                advertisement.getSharedEquipment().stream().map(Equipment::getCode).collect(Collectors.toList()),
                advertisement.getPaymentType().stream().map(PaymentType::getCode)
                        .collect(Collectors.toList()),
                Arrays.asList(advertisement.getRentalRulesObject().split(","))
        );
    }

    public AdvertisementShort mapAdvertisementToAdvertisementShort(Advertisement advertisement, Binary photo, HostResponse hostResponse) {
        return new AdvertisementShort(
                hostResponse,
                advertisement.getHostStreet(),
                photo,
                advertisement.getRoomArea(),
                advertisement.getNumBeds(),
                advertisement.getUsedBeds(),
                advertisement.getLanguage()
        );
    }


    public Advertisement mapAdvertisementDTOToAdvertisement(AdvertisementRequest advertisementRequest,
                                                            List<RoomPhoto> photos,
                                                            List<Price> priceList) {
        return new Advertisement(
                advertisementRequest.getHostId(),
                advertisementRequest.getPostCode(),
                advertisementRequest.getHostStreet(),
                photos,
                advertisementRequest.getRoomDescription(),
                advertisementRequest.getRoomArea(),
                advertisementRequest.getNumBeds(),
                advertisementRequest.getUsedBeds(),
                priceList,
                advertisementRequest.getSharedBeds(),
                advertisementRequest.getLanguage(),
                equipmentService.getEquipmentsById(advertisementRequest.getRoomEquipment()),
                equipmentService.getEquipmentsById(advertisementRequest.getSharedEquipment()),
                paymentTypeService.getAllPaymentTypeById(advertisementRequest.getPaymentType()),
                advertisementRequest.getRentalRules().toString()
        );
    }
}
