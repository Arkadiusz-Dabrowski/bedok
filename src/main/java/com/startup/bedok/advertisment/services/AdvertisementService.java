package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.AdvertisementGroup;
import com.startup.bedok.advertisment.model.entity.District;
import com.startup.bedok.advertisment.model.mapper.AdvertisementMapper;
import com.startup.bedok.advertisment.model.request.*;
import com.startup.bedok.advertisment.model.response.*;
import com.startup.bedok.advertisment.repository.AdvertisementCriteriaRepository;
import com.startup.bedok.advertisment.repository.AdvertisementGroupRepository;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import com.startup.bedok.advertisment.repository.DistrictRepository;
import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.datahelper.DataGenerator;
import com.startup.bedok.exceptions.AdvertisementNotExistsException;
import com.startup.bedok.global.SimpleResponse;
import com.startup.bedok.minio.MinioService;
import com.startup.bedok.user.model.UserShortResponse;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;
    private final UserService userService;
    private final AdvertisementCriteriaRepository advertisementCriteriaRepository;
    private final DataGenerator dataGenerator;
    private final DistrictRepository districtRepository;
    private final AdvertisementGroupRepository advertisementGroupRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final MinioService minioService;

    @Transactional
    public AdvertisementCreateResponse createAdvertisement(@Valid AdvertisementRequest advertisementRequest, String token) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        userService.checkIfHostExists(userId);
        if(advertisementRequest.getWeeklyPrice() == null){
            advertisementRequest.setWeeklyPrice(advertisementRequest.getDailyPrice());
        }
        if(advertisementRequest.getMonthlyPrice() == null){
            advertisementRequest.setMonthlyPrice(advertisementRequest.getMonthlyPrice());
        }
        return new AdvertisementCreateResponse(advertisementRepository
                .save(advertisementMapper.mapAdvertisementRequestToAdvertisement(advertisementRequest, userId))
                .getId().toString());
    }

    @Transactional
    public AdvertisementResponse updateAdvertisement(@Valid AdvertisementUpdateRequest advertisementRequest, UUID advertisementId, String token) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotExistsException(advertisementId.toString()));
        isAdvertisementBelongToUser(token, advertisement);
        Advertisement advertisementEntity = advertisementMapper.updateAdvertisementFromRequest(advertisement, advertisementRequest);
        return advertisementMapper.mapAdvertisementToAdvertisementDTO(advertisementEntity, null);
    }

    @Transactional
    public AdvertisementResponse getAdvertisementDTOById(UUID advertisementId) {
        Advertisement advertisement = advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotExistsException(advertisementId.toString()));
        String photosLocation = "advertisement/" + advertisementId + "/";
        List<String> listOfPhotosUrls = minioService.getListOfUrls(photosLocation);
        return advertisementMapper.mapAdvertisementToAdvertisementDTO(advertisement, listOfPhotosUrls);
    }

    @Transactional
    public Advertisement getAdvertisementById(UUID advertisementId) {
        return advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotExistsException(advertisementId.toString()));
    }

    @Transactional
    public List<AdvertisementShort> getAdvertisementsList() {
        return advertisementRepository.findAll().stream()
                .filter(Advertisement::isActive)
                .map(advertisement -> {
                    UserShortResponse userResponse = userService
                            .getUserShortResponseByID(advertisement.getHostId());
                    String photosLocation = "advertisement/" + advertisement.getId() + "/";
                    List<String> listOfPhotosUrls = minioService.getListOfUrls(photosLocation);

                    return advertisementMapper.mapAdvertisementToAdvertisementShort(advertisement, listOfPhotosUrls, userResponse);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public SimpleResponse saveRoomPhotos(List<MultipartFile> photos, UUID advertisementId, String token) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotExistsException(advertisementId.toString()));
        isAdvertisementBelongToUser(token, advertisement);
        photos.stream().forEach(photo -> {
            String photoId = "advertisement/" + advertisementId + "/" + photo.getOriginalFilename();
            minioService.addPhotoToMinio(photoId, photo);
        });
        return new SimpleResponse("Photos are uploaded successfully");
    }


    public List<AdvertisementShort> getAdvertisementListByHostId(String token) {
        UUID hostId = jwtTokenUtil.getUserIdFromToken(token);
        userService.checkIfHostExists(hostId);
        return advertisementRepository.findAllByHostId(hostId).stream()
                .map(advertisement -> {
                    UserShortResponse userResponse = userService
                            .getUserShortResponseByID(advertisement.getHostId());
                    String photosLocation = "advertisement/" + advertisement.getId() + "/";
                    List<String> listOfPhotosUrls = minioService.getListOfUrls(photosLocation);

                    return advertisementMapper.mapAdvertisementToAdvertisementShort(advertisement, listOfPhotosUrls, userResponse);
                })
                .collect(Collectors.toList());
    }

    public Page<AdvertisementShort> findAllWithFilters(AdvertisementMultisearch advertisementMultisearch) {
        Page<Advertisement> pageOfAdvertisements = advertisementCriteriaRepository
                .findAllWithFilters(advertisementMultisearch);
        List<AdvertisementShort> listOfAdvertisements = pageOfAdvertisements.stream()
                .map(advertisement -> {
                    UserShortResponse userResponse = userService
                            .getUserShortResponseByID(advertisement.getHostId());
                    String photosLocation = "advertisement/" + advertisement.getId() + "/";
                    List<String> listOfPhotosUrls = minioService.getListOfUrls(photosLocation);
                    return advertisementMapper
                            .mapAdvertisementToAdvertisementShort(advertisement,
                                    listOfPhotosUrls,
                                    userResponse);
                }).toList();
        return new PageImpl<>(listOfAdvertisements, pageOfAdvertisements.getPageable(),
                pageOfAdvertisements.getTotalElements());
    }

    public Page<AdvertisementGroupCriteriaResponse> findAllGroupsWithFilters(AdvertisementMultisearch advertisementMultisearch) {
        Page<AdvertisementGroupCriteriaResponse> pageOfAdvertisements = advertisementCriteriaRepository.findAllWithFiltersForGroup(advertisementMultisearch);
        pageOfAdvertisements.forEach(group -> {
            advertisementRepository.findAllByAdvertisementGroup(advertisementGroupRepository
                    .findById(group.getAdvertisementGroupId()).get()).forEach(advertisement -> {
                AdvertisementFromGroupResponse advertisementFromGroupResponse =
                        checkIfRoomIsFreeInSelectedDate(advertisement, advertisementMultisearch.getDateFrom(), advertisementMultisearch.getDateTo());
                if (advertisementFromGroupResponse.bedsNumber() > 0) {
                    UserShortResponse userResponse = userService
                            .getUserShortResponseByID(advertisement.getHostId());
                    String photosLocation = "advertisement/" + advertisement.getId() + "/";
                    List<String> listOfPhotosUrls = minioService.getListOfUrls(photosLocation);
                    group.addAdvertisementToList(advertisementMapper
                            .mapAdvertisementToAdvertisementShort(advertisementFromGroupResponse.advertisement(), listOfPhotosUrls, userResponse));
                }
            });

        });
        return pageOfAdvertisements;
    }

    public List<Advertisement> createSomeRandomAdvertisements() {
        return dataGenerator.createSomeAdvertisementData();
    }

    @Transactional
    public AdvertisementChangeStatusResponse deleteAdvertisementById(UUID id, String token) {
        Advertisement advertisementToDelete = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotExistsException(id.toString()));
        isAdvertisementBelongToUser(token, advertisementToDelete);
        String photosLocation = "advertisement/" + id + "/";
        minioService.deleteObjectsFromLocation(photosLocation);
        advertisementRepository.delete(advertisementToDelete);
        return new AdvertisementChangeStatusResponse(String.format("Your advertisement with title: %s is deleted", advertisementToDelete.getTitle()));
    }

    @Transactional
    public AdvertisementChangeStatusResponse deactivateAdvertisementById(UUID id, String token) {
        Advertisement advertisementToDeactivate = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotExistsException(id.toString()));
        isAdvertisementBelongToUser(token, advertisementToDeactivate);
        advertisementToDeactivate.setActive(false);
        return new AdvertisementChangeStatusResponse(String.format("Your advertisement with title: %s is deactivated", advertisementToDeactivate.getTitle()));
    }

    @Transactional
    public AdvertisementChangeStatusResponse activateAdvertisementById(UUID id, String token) {
        Advertisement advertisementToActivate = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotExistsException(id.toString()));
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
    public AdvertisementResponse deletePhotosFromAdvertisement(DeleteAdvertisementPhotoRequest deleteAdvertisementPhotoRequest,
                                                               UUID advertisementId,
                                                               String token) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(() -> new AdvertisementNotExistsException(advertisementId));
        isAdvertisementBelongToUser(token, advertisement);
//        deleteAdvertisementPhotoRequest.photoIds().forEach(id -> {
//            roomPhotosRepository.deleteByPhotoId(id);
//            advertisementPhotoService.deletePhotoFromAdvertisement(id);
//        });
//        return roomPhotosRepository.findAllByAdvertisementId(advertisementId);
        String photosLocation = "advertisement/" + advertisementId + "/";
        minioService.deleteObjectsFromLocation(photosLocation);
        return getAdvertisementDTOById(advertisementId);
    }
}
