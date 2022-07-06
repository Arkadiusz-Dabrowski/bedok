package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.AdvertisementDTO;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.startup.bedok.advertisment.model.mapper.AdvertisementMapper.*;

@RequiredArgsConstructor
@Service
public class AdvertisementService {

    private AdvertisementRepository advertisementRepository;
    private AdvertisementPhotoService advertisementPhotoService;

    public void createAdvertisement(AdvertisementDTO advertisementDTO) {
       List<String> photosID = advertisementPhotoService.
                saveAdvertisementPhotos(advertisementDTO.getRoomPhotos());
        mapAdvertisementDTOToAdvertisement(advertisementDTO, photosID);

    }
}
