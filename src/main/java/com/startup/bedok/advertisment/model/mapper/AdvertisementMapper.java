package com.startup.bedok.advertisment.model.mapper;

import com.startup.bedok.advertisment.model.Advertisement;
import com.startup.bedok.advertisment.model.AdvertisementDTO;
import com.startup.bedok.helper.model.RoomPhoto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AdvertisementMapper {

    public static AdvertisementDTO mapAdvertisementToAdvertisementDTO(Advertisement advertisement, List<MultipartFile> photos) {
        return new AdvertisementDTO(advertisement.getHostId(),
                        advertisement.getPostCode(),
                        advertisement.getHostStreet(),
                advertisement.getRoomId(),
                photos,
                advertisement.getRoomDescription(),
                advertisement.getRoomArea(),
                advertisement.getNumBeds(),
                advertisement.getPriceList(),
                advertisement.isSharedBeds(),
                advertisement.getLanguage(),
                advertisement.getRoomEquipment(),
                advertisement.getSharedEquipment(),
                advertisement.getPaymentType(),
                advertisement.getRentalRulesObject()
                );
    }


    public static Advertisement mapAdvertisementDTOToAdvertisement(AdvertisementDTO advertisementDTO, List<String> photosId) {
        return new Advertisement(
                advertisementDTO.getHostId(),
                advertisementDTO.getPostCode(),
                advertisementDTO.getHostStreet(),
                advertisementDTO.getRoomId(),
                ,
                advertisementDTO.getRoomDescription(),
                advertisementDTO.getRoomArea(),
                advertisementDTO.getNumBeds(),
                advertisementDTO.getPriceList(),
                advertisementDTO.isSharedBeds(),
                advertisementDTO.getLanguage(),
                advertisementDTO.getRoomEquipment(),
                advertisementDTO.getSharedEquipment(),
                advertisementDTO.getPaymentType(),
                advertisementDTO.getRentalRulesObject()
        );
    }
}
