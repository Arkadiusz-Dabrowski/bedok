package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.exception.AdvertisementNotExistsException;
import com.startup.bedok.datahelper.DataGenerator;
import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.AdvertisementPhoto;
import com.startup.bedok.advertisment.model.entity.Price;
import com.startup.bedok.advertisment.model.entity.RoomPhoto;
import com.startup.bedok.advertisment.model.mapper.AdvertisementMapper;
import com.startup.bedok.advertisment.model.request.AdvertisementMultisearch;
import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
import com.startup.bedok.advertisment.model.request.AdvertisementShort;
import com.startup.bedok.advertisment.model.response.AdvertisementDTO;
import com.startup.bedok.advertisment.repository.AdvertisementCriteriaRepository;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import com.startup.bedok.advertisment.repository.RoomPhotosRepository;
import com.startup.bedok.host.model.HostResponse;
import com.startup.bedok.host.service.HostService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final AdvertisementCriteriaRepository advertisementCriteriaRepository;

    private final RoomPhotosRepository roomPhotosRepository;
    private final DataGenerator dataGenerator;

    @Transactional
    public UUID createAdvertisement(AdvertisementRequest advertisementRequest) {
        hostService.checkIfHostExists(advertisementRequest.getHostId());

        List<Price> priceList = priceService.addPriceList(advertisementRequest.getPriceList());
        return advertisementRepository
                .save(advertisementMapper.mapAdvertisementDTOToAdvertisement(advertisementRequest, priceList))
                .getId();
    }

    @Transactional
    public Advertisement updateAdvertisement(AdvertisementRequest advertisementRequest, UUID advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotExistsException(advertisementId.toString()));
        return advertisementMapper.updateAdvertisementFromRequest(advertisement, advertisementRequest);
    }

    @Transactional
    public AdvertisementDTO getAdvertisementById(UUID advertisementId) {
        Advertisement advertisement = advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotExistsException(advertisementId.toString()));
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
                    HostResponse hostResponse = hostService
                            .getHostByID(advertisement.getHostId());
                    RoomPhoto mainPhoto = null;
                    if (!advertisement.getRoomPhotos().isEmpty())
                        mainPhoto = advertisement.getRoomPhotos().get(0);

                    AdvertisementPhoto advertisementPhoto = advertisementPhotoService
                            .getPhotoById(mainPhoto.getPhotoId());

                    return advertisementMapper.mapAdvertisementToAdvertisementShort(advertisement, advertisementPhoto.getImage(), hostResponse);
                })
                .collect(Collectors.toList());
    }

    public String saveRoomPhotos(List<MultipartFile> photos, UUID advertisementId) {
        List<RoomPhoto> roomPhotos = advertisementPhotoService.saveAdvertisementPhotos(photos);
        return addPhotosToAdvertisement(roomPhotos, advertisementId);
    }

    private String addPhotosToAdvertisement(List<RoomPhoto> roomPhotos, UUID advertisementId) {
        roomPhotosRepository.saveAll(roomPhotos);
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotExistsException(advertisementId.toString()));
        advertisement.getRoomPhotos().addAll(roomPhotos);
        advertisementRepository.save(advertisement);
        if (advertisementRepository.getById(advertisementId).getRoomPhotos().size() > 0)
            return " added";
        return "fail";
    }

    public List<AdvertisementShort> getAdvertisementListByHostId(UUID hostId) {
        return advertisementRepository.findAllByHostId(hostId).stream()
                .map(advertisement -> {
                    HostResponse hostResponse = hostService
                            .getHostByID(advertisement.getHostId());
                    RoomPhoto mainPhoto = null;
                    if (!advertisement.getRoomPhotos().isEmpty())
                        mainPhoto = advertisement.getRoomPhotos().get(0);

                    AdvertisementPhoto advertisementPhoto = advertisementPhotoService
                            .getPhotoById(mainPhoto.getPhotoId());

                    return advertisementMapper.mapAdvertisementToAdvertisementShort(advertisement, advertisementPhoto.getImage(), hostResponse);
                })
                .collect(Collectors.toList());
    }

    public Page<AdvertisementShort> findAllWithFilters(AdvertisementMultisearch advertisementMultisearch) {
        Page<Advertisement> pageOfAdvertisements = advertisementCriteriaRepository
                .findAllWithFilters(advertisementMultisearch);
        List<AdvertisementShort> listOfAdvertisements = pageOfAdvertisements.stream()
                .map(advertisement -> {
                    HostResponse hostResponse = hostService
                            .getHostByID(advertisement.getHostId());
                    RoomPhoto mainPhoto = null;
                    if (!advertisement.getRoomPhotos().isEmpty())
                        mainPhoto = advertisement.getRoomPhotos().get(0);

                    AdvertisementPhoto advertisementPhoto = advertisementPhotoService
                            .getPhotoById(mainPhoto.getPhotoId());
                    return advertisementMapper
                            .mapAdvertisementToAdvertisementShort(advertisement,
                                    advertisementPhoto.getImage(),
                                    hostResponse);
                }).toList();
        return new PageImpl<>(listOfAdvertisements, pageOfAdvertisements.getPageable(),
                pageOfAdvertisements.getTotalElements());
    }

    public void createSomeRandomAdvertisements() {
        dataGenerator.createSomeAdvertisementData();
    }

    public String createSomePhotosForRandomAdvertisements() throws IOException {
        return dataGenerator.createSomeAdvertisementPhotos();
    }

    public String saveRoomPhotostoAll(List<MultipartFile> photos) {
        return advertisementRepository.findAll().stream().filter(x -> x.getRoomPhotos().isEmpty()).map(x -> {
            List<RoomPhoto> roomPhotos = advertisementPhotoService.saveAdvertisementPhotos(photos);
            return addPhotosToAdvertisement(roomPhotos, x.getId());
        }).collect(Collectors.joining());
    }
}
