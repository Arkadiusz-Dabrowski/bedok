package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.entity.*;
import com.startup.bedok.advertisment.model.mapper.AdvertisementMapper;
import com.startup.bedok.advertisment.model.request.*;
import com.startup.bedok.advertisment.model.response.*;
import com.startup.bedok.advertisment.repository.*;
import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.datahelper.DataGenerator;
import com.startup.bedok.exceptions.AdvertisementNoExistsException;
import com.startup.bedok.global.PhotoResponse;
import com.startup.bedok.global.SimpleResponse;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
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
    private final AdvertisementGroupRepository advertisementGroupRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public AdvertisementCreateResponse createAdvertisement(@Valid AdvertisementRequest advertisementRequest, String token) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        userService.checkIfHostExists(userId);
        return new AdvertisementCreateResponse(advertisementRepository
                .save(advertisementMapper.mapAdvertisementRequestToAdvertisement(advertisementRequest, userId))
                .getId().toString());
    }

    @Transactional
    public AdvertisementResponse updateAdvertisement(@Valid AdvertisementUpdateRequest advertisementRequest, UUID advertisementId, String token) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNoExistsException(advertisementId.toString()));
        isAdvertisementBelongToUser(token, advertisement);
        Advertisement advertisementEntity = advertisementMapper.updateAdvertisementFromRequest(advertisement, advertisementRequest);
        return advertisementMapper.mapAdvertisementToAdvertisementDTO(advertisementEntity, null);
    }

    @Transactional
    public AdvertisementResponse getAdvertisementDTOById(UUID advertisementId) {
        Advertisement advertisement = advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNoExistsException(advertisementId.toString()));
        List<RoomPhoto> roomPhotos = roomPhotosRepository.findAllByAdvertisementId(advertisementId);
        List<PhotoResponse> photos = advertisementPhotoService
                .getPhotosByIds(roomPhotos
                        .stream()
                        .map(RoomPhoto::getPhotoId)
                        .collect(Collectors.toList()))
                .stream()
                .map(photo -> {
                    Binary image = photo.getImage();
                    return new PhotoResponse(photo.getId(), image);
                })
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
                .filter(Advertisement::isActive)
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

    public SimpleResponse saveRoomPhotos(List<MultipartFile> photos, UUID advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNoExistsException(advertisementId.toString()));
         advertisementPhotoService.saveAdvertisementPhotos(photos, advertisement).forEach(photo -> roomPhotosRepository.save(new RoomPhoto(photo.getPhotoId(), advertisement)));
         return new SimpleResponse("Photos are uploaded sucesfully");
    }


    public List<AdvertisementShort> getAdvertisementListByHostId(String token) {
        UUID hostId = jwtTokenUtil.getUserIdFromToken(token);
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
                    return advertisementMapper
                            .mapAdvertisementToAdvertisementShort(advertisement,
                                    advertisementPhotos,
                                    userResponse);
                }).toList();
        return new PageImpl<>(listOfAdvertisements, pageOfAdvertisements.getPageable(),
                pageOfAdvertisements.getTotalElements());
    }

    public Page<AdvertisementGroupCriteriaResponse> findAllGroupsWithFilters(AdvertisementMultisearch advertisementMultisearch) {
        Page<AdvertisementGroupCriteriaResponse> pageOfAdvertisements = advertisementCriteriaRepository.findAllWithFiltersForGroup(advertisementMultisearch);
        pageOfAdvertisements.forEach(group -> {
            advertisementRepository.findAllByAdvertisementGroup(advertisementGroupRepository.findById(group.getAdvertisementGroupId()).get()).forEach(advertisement -> {
                AdvertisementFromGroupResponse advertisementFromGroupResponse = checkIfRoomIsFreeInSelectedDate(advertisement, advertisementMultisearch.getDateFrom(), advertisementMultisearch.getDateTo());
                if(advertisementFromGroupResponse.bedsNumber() > 0) {
                    UserResponse userResponse = userService
                            .getUserResponseByID(advertisement.getHostId());
                    List<RoomPhoto> photos = roomPhotosRepository.findAllByAdvertisementId(advertisement.getId());
                    if (!photos.isEmpty())
                        photos = photos.stream().limit(5).collect(Collectors.toList());
                    List<Binary> advertisementPhotos = advertisementPhotoService
                            .getPhotos(photos).stream().map(AdvertisementPhoto::getImage).toList();
                    group.addAdvertisementToList(advertisementMapper.mapAdvertisementToAdvertisementShort(advertisementFromGroupResponse.advertisement(),advertisementPhotos,userResponse));
                }
            });

        });
        return pageOfAdvertisements;
    }

    public List<Advertisement> createSomeRandomAdvertisements() {
        return dataGenerator.createSomeAdvertisementData();
    }

    public String createSomePhotosForRandomAdvertisements() throws IOException {
        return dataGenerator.createSomeAdvertisementPhotos().toString();
    }


    @Transactional
    public AdvertisementChangeStatusResponse deleteAdvertisementById(UUID id, String token) {
        Advertisement advertisementToDelete = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNoExistsException(id.toString()));
        isAdvertisementBelongToUser(token, advertisementToDelete);
        roomPhotosRepository.findAllByAdvertisementId(id).forEach(photo -> {
            advertisementPhotoService.deletePhotoFromAdvertisement(photo);
            roomPhotosRepository.delete(photo);
        });
        advertisementRepository.delete(advertisementToDelete);
        return new AdvertisementChangeStatusResponse(String.format("Your advertisement with title: %s is deleted", advertisementToDelete.getTitle()));
    }

    @Transactional
    public AdvertisementChangeStatusResponse deactivateAdvertisementById(UUID id, String token) {
        Advertisement advertisementToDeactivate = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNoExistsException(id.toString()));
        isAdvertisementBelongToUser(token, advertisementToDeactivate);
        advertisementToDeactivate.setActive(false);
        return new AdvertisementChangeStatusResponse(String.format("Your advertisement with title: %s is deactivated", advertisementToDeactivate.getTitle()));
    }

    @Transactional
    public AdvertisementChangeStatusResponse activateAdvertisementById(UUID id, String token) {
        Advertisement advertisementToActivate = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNoExistsException(id.toString()));
        isAdvertisementBelongToUser(token, advertisementToActivate);
        advertisementToActivate.setActive(true);
        return new AdvertisementChangeStatusResponse(String.format("Your advertisement with title: %s is activated", advertisementToActivate.getTitle()));

    }

    private void isAdvertisementBelongToUser(String token, Advertisement advertisement) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        if (!advertisement.getHostId().equals(userId)) {
            throw new IllegalArgumentException("Advertisement does not belong dateTo user");
        }
    }

    private AdvertisementFromGroupResponse checkIfRoomIsFreeInSelectedDate(Advertisement advertisement, LocalDate dateFrom, LocalDate dateTo) {
        int numberOfFreeBeds = (int) (advertisement.getNumBeds() - advertisement.getReservations().stream().filter(reservation -> (dateFrom.equals(reservation.getDateFrom())) || dateTo.isEqual(reservation.getDateTo())
                        || (dateFrom.isBefore(reservation.getDateFrom()) && dateTo.isAfter(reservation.getDateFrom()))
                        || (dateFrom.isAfter(reservation.getDateFrom()) && !dateFrom.isAfter(reservation.getDateTo()))
                )
                .count());
        return new AdvertisementFromGroupResponse(advertisement, numberOfFreeBeds);
    }

    public Map<String, List<String>> getDistrictsCollection() {
        return districtRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        District::getCityName,
                        Collectors.mapping(District::getName, Collectors.toList())
                ));
    }


    @Transactional
    public UUID createAdvertisementGroup(AdvertisementGroupCreate advertisementGroupCreate) {
        List<Advertisement> advertisements = advertisementGroupCreate.getAdvertisementsId().stream().map(this::getAdvertisementById).toList();
        AdvertisementGroup advertisementGroup = advertisementGroupRepository.save(new AdvertisementGroup(advertisementGroupCreate.getHostId(), advertisementGroupCreate.getCity(), advertisementGroupCreate.getTitle(), advertisementGroupCreate.getDescription(), advertisements));
        advertisements.forEach(advertisement -> advertisement.setAdvertisementGroup(advertisementGroup));
        return UUID.randomUUID();
    }

    @Transactional
    public List<RoomPhoto> deletePhotosFromAdvertisement(DeleteAdvertisementPhotoRequest deleteAdvertisementPhotoRequest,
                                                       UUID advertisementId,
                                                       String token) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(() -> new AdvertisementNoExistsException(advertisementId));
        isAdvertisementBelongToUser(token, advertisement);
        deleteAdvertisementPhotoRequest.photoIds().forEach(id -> {
            roomPhotosRepository.deleteByPhotoId(id);
            advertisementPhotoService.deletePhotoFromAdvertisement(id);
        });
        return roomPhotosRepository.findAllByAdvertisementId(advertisementId);
    }
}
