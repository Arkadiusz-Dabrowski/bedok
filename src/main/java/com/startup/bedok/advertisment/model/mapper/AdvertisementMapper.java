package com.startup.bedok.advertisment.model.mapper;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
import com.startup.bedok.advertisment.model.request.AdvertisementUpdateRequest;
import com.startup.bedok.advertisment.model.response.AdvertisementResponse;
import com.startup.bedok.advertisment.model.response.AdvertisementShort;
import com.startup.bedok.user.model.UserShortResponse;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.startup.bedok.guest.model.GuestMapper.mapGuestToGuestResponse;

@Component
@RequiredArgsConstructor
public class AdvertisementMapper {

    private final UserService userService;

    public AdvertisementResponse mapAdvertisementToAdvertisementDTO(Advertisement advertisement, List<String> photos) {
        return new AdvertisementResponse(
                userService.getUserResponseByID(advertisement.getHostId()),
                advertisement.getId(),
                advertisement.getTitle(),
                advertisement.getCity(),
                advertisement.getDistrict(),
                advertisement.getPostCode(),
                advertisement.getStreetName(),
                photos,
                advertisement.getRoomDescription(),
                advertisement.getRoomGender(),
                advertisement.getReservations()
                        .stream()
                        .map(reservation -> mapGuestToGuestResponse(reservation.getGuest()))
                        .toList(),
                advertisement.getRoomArea(),
                advertisement.getNumBeds(),
                advertisement.getDailyPrice(),
                advertisement.getLanguage(),
                advertisement.isIronRoom(),
                advertisement.isHooverRoom(),
                advertisement.isTelevisionRoom(),
                advertisement.isRadioRoom(),
                advertisement.isBalconyRoom(),
                advertisement.isBathroom(),
                advertisement.isIronShared(),
                advertisement.isHooverShared(),
                advertisement.isTelevisionShared(),
                advertisement.isRadioShared(),
                advertisement.isBalconyShared(),
                advertisement.isCache(),
                advertisement.isTransfer(),
                Arrays.asList(advertisement.getRentalRulesObject().split(",")),
                advertisement.isActive()
        );
    }

    public AdvertisementShort mapAdvertisementToAdvertisementShort(Advertisement advertisement, List<String> photos, UserShortResponse userResponse) {
        return new AdvertisementShort(
                advertisement.getId(),
                userResponse.getId(),
                advertisement.getTitle(),
                advertisement.getCity(),
                advertisement.getDistrict(),
                advertisement.getNumBeds(),
                advertisement.getRoomDescription(),
                advertisement.getRoomGender(),
                advertisement.getReservations()
                        .stream()
                        .map(reservation -> mapGuestToGuestResponse(reservation.getGuest()))
                        .toList(),
                advertisement.getDailyPrice(),
                advertisement.getStreetName(),
                advertisement.getRoomArea(),
                userResponse,
                photos,
                advertisement.isIronRoom(),
                advertisement.isHooverRoom(),
                advertisement.isTelevisionRoom(),
                advertisement.isRadioRoom(),
                advertisement.isBalconyRoom(),
                advertisement.isBathroom(),
                advertisement.isIronShared(),
                advertisement.isHooverShared(),
                advertisement.isTelevisionShared(),
                advertisement.isRadioShared(),
                advertisement.isBalconyShared(),
                advertisement.isActive()
        );
    }

    public Advertisement mapAdvertisementRequestToAdvertisement(AdvertisementRequest advertisementRequest,
                                                                UUID userId) {
        return new Advertisement(
                userId,
                advertisementRequest.getTitle(),
                advertisementRequest.getCity(),
                advertisementRequest.getDistrict(),
                advertisementRequest.getRoomGender(),
                advertisementRequest.getPostCode(),
                advertisementRequest.getStreetName(),
                advertisementRequest.getRoomDescription(),
                advertisementRequest.getRoomArea(),
                advertisementRequest.getNumBeds(),
                advertisementRequest.getDailyPrice(),
                advertisementRequest.getWeeklyPrice(),
                advertisementRequest.getMonthlyPrice(),
                advertisementRequest.getLanguage(),
                advertisementRequest.isIronRoom(),
                advertisementRequest.isHooverRoom(),
                advertisementRequest.isTelevisionRoom(),
                advertisementRequest.isRadioRoom(),
                advertisementRequest.isBalconyRoom(),
                advertisementRequest.isBathroom(),
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

    public Advertisement updateAdvertisementFromRequest(Advertisement advertisement,
                                                        AdvertisementUpdateRequest request) {
        advertisement.setTitle(request.getTitle());
        advertisement.setCity(request.getCity());
        advertisement.setDistrict(request.getDistrict());
        advertisement.setRoomGender(request.getRoomGender());
        advertisement.setPostCode(request.getPostCode());
        advertisement.setStreetName(request.getStreetName());
        advertisement.setRoomDescription(request.getRoomDescription());
        advertisement.setRoomArea(request.getRoomArea());
        advertisement.setNumBeds(request.getNumBeds());
        advertisement.setDailyPrice(request.getDailyPrice());
        if (request.getWeeklyPrice() != null)
            advertisement.setWeeklyPrice(request.getWeeklyPrice());
        if (request.getMonthlyPrice() != null)
            advertisement.setMonthlyPrice(request.getMonthlyPrice());
        advertisement.setLanguage(request.getLanguage());
        advertisement.setIronRoom(request.isIronRoom());
        advertisement.setHooverRoom(request.isHooverRoom());
        advertisement.setTelevisionRoom(request.isTelevisionRoom());
        advertisement.setRadioRoom(request.isRadioRoom());
        advertisement.setBalconyRoom(request.isBalconyRoom());
        advertisement.setBathroom(request.isBathroom());
        advertisement.setIronShared(request.isIronShared());
        advertisement.setHooverShared(request.isHooverShared());
        advertisement.setTelevisionShared(request.isTelevisionShared());
        advertisement.setRadioShared(request.isRadioShared());
        advertisement.setBalconyShared(request.isBalconyShared());
        advertisement.setCache(request.isCache());
        advertisement.setTransfer(request.isTransfer());
        advertisement.setRentalRulesObject(addRentalRules(request.getRentalRules()));
        advertisement.setUpdateDate(Instant.now().getEpochSecond());
        return advertisement;
    }

    private static String addRentalRules(List<String> rentalRules) {
        if (!rentalRules.isEmpty())
            return String.join(",", rentalRules);

        return "";
    }
}
