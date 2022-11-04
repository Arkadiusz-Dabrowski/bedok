package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.*;
import com.startup.bedok.advertisment.model.mapper.AdvertisementMapper;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import com.startup.bedok.host.model.HostResponse;
import com.startup.bedok.host.service.HostService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementPhotoService advertisementPhotoService;
    private final AdvertisementMapper advertisementMapper;
    private final PriceService priceService;
    private final HostService hostService;

    @Transactional
    public UUID createAdvertisement(AdvertisementRequest advertisementRequest) {
        hostService.checkIfHostExists(advertisementRequest.getHostId());

        List<Price> priceList = priceService.addPriceList(advertisementRequest.getPriceList());
        return advertisementRepository
                .save(advertisementMapper.mapAdvertisementDTOToAdvertisement(advertisementRequest, priceList))
                .getId();

    }

    @Transactional
    public AdvertisementDTO getAdvertisementById(UUID advertisementId) {
        Advertisement advertisement = advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new RuntimeException(String.format("there is no Advertisement with uuid %s", advertisementId)));
        List<Binary> photos = advertisementPhotoService
                .getPhotosByIds(advertisement.getRoomPhotos()
                        .stream()
                        .map(RoomPhoto::getPhotoId)
                        .collect(Collectors.toList()))
                .stream()
                .map(AdvertisementPhoto::getImage)
                .collect(Collectors.toList());
        return advertisementMapper.mapAdvertisementToAdvertisementDTO(advertisement, photos);
    }

    @Transactional
    public List<AdvertisementShort> getAdvertisementsList() {
        return advertisementRepository.findAll().stream()
                .map(advertisement -> {
                    AdvertisementPhoto advertisementPhoto = advertisementPhotoService
                            .getPhotoById(advertisement
                                    .getRoomPhotos()
                                    .get(0)
                                    .getPhotoId());
                    HostResponse hostResponse = hostService
                            .getHostByID(advertisement.getHostId());
                   return advertisementMapper
                           .mapAdvertisementToAdvertisementShort(advertisement,
                                   advertisementPhoto.getImage(),
                                   hostResponse);})
                .collect(Collectors.toList());
    }

    public String saveRoomPhotos(List<MultipartFile> photos, UUID advertisementId) {
        List<RoomPhoto> roomPhotos = advertisementPhotoService.saveAdvertisementPhotos(photos);
        return addPhotosToAdvertisement(roomPhotos, advertisementId);
    }

    @Transactional
    private String addPhotosToAdvertisement(List<RoomPhoto> roomPhotos, UUID advertisementId){
        advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new RuntimeException(String.format("there is no Advertisement with uuid %s", advertisementId)))
                .setRoomPhotos(roomPhotos);
        if(advertisementRepository.getById(advertisementId).getRoomPhotos().size() > 0)
            return " added";
        return "fail";
    }
}
