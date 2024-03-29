package com.startup.bedok.advertisment.services;

import com.startup.bedok.exceptions.AdvertisementNoExistsException;
import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.AdvertisementPhoto;
import com.startup.bedok.advertisment.model.entity.District;
import com.startup.bedok.advertisment.model.entity.RoomPhoto;
import com.startup.bedok.advertisment.model.mapper.AdvertisementMapper;
import com.startup.bedok.advertisment.model.request.AdvertisementMultisearch;
import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
import com.startup.bedok.advertisment.model.response.AdvertisementShort;
import com.startup.bedok.advertisment.model.response.AdvertisementResponse;
import com.startup.bedok.advertisment.repository.AdvertisementCriteriaRepository;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import com.startup.bedok.advertisment.repository.DistrictRepository;
import com.startup.bedok.advertisment.repository.RoomPhotosRepository;
import com.startup.bedok.datahelper.DataGenerator;
import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementPhotoService advertisementPhotoService;
    private final AdvertisementMapper advertisementMapper;
    private final UserService userService;
    private final AdvertisementCriteriaRepository advertisementCriteriaRepository;

    private final RoomPhotosRepository roomPhotosRepository;
    private final DataGenerator dataGenerator;
    private final DistrictRepository districtRepository;
    private final JwtTokenUtil jwtTokenUtil;
    @Transactional
    public UUID createAdvertisement(AdvertisementRequest advertisementRequest, String token) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        userService.checkIfHostExists(userId);
        return advertisementRepository
                .save(advertisementMapper.mapAdvertisementRequestToAdvertisement(advertisementRequest, userId))
                .getId();
    }

    @Transactional
    public Advertisement updateAdvertisement(AdvertisementRequest advertisementRequest, UUID advertisementId, String token) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNoExistsException(advertisementId.toString()));
        if(!advertisement.getHostId().equals(userId)) {
            throw new IllegalArgumentException("Advertisement does not belong to user");
        }
        return advertisementMapper.updateAdvertisementFromRequest(advertisement, advertisementRequest);
    }

    @Transactional
    public AdvertisementResponse getAdvertisementDTOById(UUID advertisementId) {
        Advertisement advertisement = advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNoExistsException(advertisementId.toString()));
        List<RoomPhoto> roomPhotos = roomPhotosRepository.findAllByAdvertisementId(advertisementId);
        List<Binary> photos = advertisementPhotoService
                .getPhotosByIds(roomPhotos
                        .stream()
                        .map(RoomPhoto::getPhotoId)
                        .collect(Collectors.toList()))
                .stream()
                .map(AdvertisementPhoto::getImage)
                .collect(Collectors.toList());
        return advertisementMapper.mapAdvertisementToAdvertisementDTO(advertisement, photos);
    }

    @Transactional
    public Advertisement getAdvertisementById(UUID advertisementId) {
        return advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNoExistsException(advertisementId.toString()));
    }

    @Transactional
    public List<AdvertisementShort> getAdvertisementsList() {
        return advertisementRepository.findAll().stream()
                .map(advertisement -> {
                    UserResponse userResponse = userService
                            .getUserResponseByID(advertisement.getHostId());
                    List<RoomPhoto> photos = roomPhotosRepository.findAllByAdvertisementId(advertisement.getId());
                    if (!photos.isEmpty())
                        photos = photos.stream().limit(5).collect(Collectors.toList());
                    List<Binary> advertisementPhotos = advertisementPhotoService
                            .getPhotos(photos).stream().map(AdvertisementPhoto::getImage).toList();

                    return advertisementMapper.mapAdvertisementToAdvertisementShort(advertisement, advertisementPhotos, userResponse);
                })
                .collect(Collectors.toList());
    }

    public String saveRoomPhotos(List<MultipartFile> photos, UUID advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNoExistsException(advertisementId.toString()));
        return advertisementPhotoService.saveAdvertisementPhotos(photos, advertisement).toString();
    }


    public List<AdvertisementShort> getAdvertisementListByHostId(UUID hostId) {
        userService.checkIfHostExists(hostId);
        return advertisementRepository.findAllByHostId(hostId).stream()
                .map(advertisement -> {
                    UserResponse userResponse = userService
                            .getUserResponseByID(advertisement.getHostId());
                    List<RoomPhoto> photos = roomPhotosRepository.findAllByAdvertisementId(advertisement.getId());
                    if (!photos.isEmpty())
                        photos = photos.stream().limit(5).collect(Collectors.toList());
                    List<Binary> advertisementPhotos = advertisementPhotoService
                            .getPhotos(photos).stream().map(AdvertisementPhoto::getImage).toList();

                    return advertisementMapper.mapAdvertisementToAdvertisementShort(advertisement, advertisementPhotos, userResponse);
                })
                .collect(Collectors.toList());
    }

    public Page<AdvertisementShort> findAllWithFilters(AdvertisementMultisearch advertisementMultisearch) {
        Page<Advertisement> pageOfAdvertisements = advertisementCriteriaRepository
                .findAllWithFilters(advertisementMultisearch);
        List<AdvertisementShort> listOfAdvertisements = pageOfAdvertisements.stream()
                .map(advertisement -> {
                    UserResponse userResponse = userService
                            .getUserResponseByID(advertisement.getHostId());
                    List<RoomPhoto> photos = roomPhotosRepository.findAllByAdvertisementId(advertisement.getId());
                    if (!photos.isEmpty())
                        photos = photos.stream().limit(5).collect(Collectors.toList());
                    List<Binary> advertisementPhotos = advertisementPhotoService
                            .getPhotos(photos).stream().map(AdvertisementPhoto::getImage).toList();
                    if(advertisementMultisearch.getDateTo() == null || advertisementMultisearch.getDateFrom() ==null) {
                        return advertisementMapper
                                .mapAdvertisementToAdvertisementShort(advertisement,
                                        advertisementPhotos,
                                        userResponse);
                    }
                    else {
                        return advertisementMapper
                                .mapAdvertisementToAdvertisementShortWithDate(advertisement,
                                        advertisementPhotos,
                                        userResponse,
                                        advertisementMultisearch.getDateFrom(),
                                        advertisementMultisearch.getDateTo());
                    }
                }).toList();
        return new PageImpl<>(listOfAdvertisements, pageOfAdvertisements.getPageable(),
                pageOfAdvertisements.getTotalElements());
    }

    public void createSomeRandomAdvertisements() {
        dataGenerator.createSomeAdvertisementData();
    }

    public String createSomePhotosForRandomAdvertisements() throws IOException {
         return dataGenerator.createSomeAdvertisementPhotos().toString();
    }


    @Transactional
    public UUID deleteAdvertisementById(UUID id) {
        Advertisement advertisementToDelete = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNoExistsException(id.toString()));
        roomPhotosRepository.findAllByAdvertisementId(id).forEach(photo -> {
            advertisementPhotoService.deletePhotoFromAdvertisement(photo);
            roomPhotosRepository.delete(photo);
        });
        advertisementRepository.delete(advertisementToDelete);
        return id;
    }

    @Transactional
    public UUID deactivateAdvertisementById(UUID id) {
        Advertisement advertisementToDeactivate = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNoExistsException(id.toString()));
        advertisementToDeactivate.setActive(false);
        return id;
    }

    public Map<String, List<String>> getDistrictsCollection() {
        return districtRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        District::getCityName,
                        Collectors.mapping(District::getName, Collectors.toList())
                ));
    }
}
