package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.Advertisement;
import com.startup.bedok.advertisment.model.AdvertisementDTO;
import com.startup.bedok.advertisment.model.RoomPhoto;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.startup.bedok.advertisment.model.mapper.AdvertisementMapper.mapAdvertisementDTOToAdvertisement;
import static com.startup.bedok.advertisment.model.mapper.AdvertisementMapper.mapAdvertisementToAdvertisementDTO;


@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementPhotoService advertisementPhotoService;

    @Transactional
    public Long createAdvertisement(AdvertisementDTO advertisementDTO) {
        List<String> photosID = advertisementPhotoService.
                saveAdvertisementPhotos(advertisementDTO.getRoomPhotos());
        return advertisementRepository
                .save(mapAdvertisementDTOToAdvertisement(advertisementDTO, photosID))
                .getId();

    }
    @Transactional
    public AdvertisementDTO getAdvertisementById(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId).get();
        List<MultipartFile> photos = advertisementPhotoService.getPhotosByIds(advertisement.getRoomPhotosUrl().stream().map(RoomPhoto::getPhotoId).collect(Collectors.toList()))
                .stream().map(photo -> (MultipartFile) photo.getImage()).collect(Collectors.toList());
        return mapAdvertisementToAdvertisementDTO(advertisement, photos);
    }

}
