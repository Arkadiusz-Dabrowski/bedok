package com.startup.bedok.advertisment.model.mapper;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.District;
import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
import com.startup.bedok.advertisment.model.response.AdvertisementResponse;
import com.startup.bedok.advertisment.model.response.AdvertisementShort;
import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.startup.bedok.guest.model.GuestMapper.mapGuestToGuestResponse;

@Component
@RequiredArgsConstructor
public class AdvertisementMapper {

    private final UserService userService;

    public AdvertisementResponse mapAdvertisementToAdvertisementDTO(Advertisement advertisement, List<Binary> photos) {
        return new AdvertisementResponse(
                userService.getUserResponseByID(advertisement.getHostId()),
                advertisement.getTitle(),
                advertisement.getCity(),
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
                advertisement.getPrice(),
                advertisement.getSharedBeds(),
                advertisement.getLanguage(),
                advertisement.isIronRoom(),
                advertisement.isHooverRoom(),
                advertisement.isTelevisionRoom(),
                advertisement.isRadioRoom(),
                advertisement.isBalconyRoom(),
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

    public AdvertisementShort mapAdvertisementToAdvertisementShort(Advertisement advertisement, List<Binary> photo, UserResponse userResponse) {
        return new AdvertisementShort(
                advertisement.getId(),
                advertisement.getTitle(),
                advertisement.getCity(),
                advertisement.getDistrict().getName(),
                advertisement.getNumBeds(),
                advertisement.getRoomDescription(),
                advertisement.getRoomGender(),
                advertisement.getReservations()
                        .stream()
                        .map(reservation -> mapGuestToGuestResponse(reservation.getGuest()))
                        .toList(),
                advertisement.getPrice(),
                advertisement.getStreetName(),
                advertisement.getRoomArea(),
                userResponse,
                photo,
                advertisement.isIronRoom(),
                advertisement.isHooverRoom(),
                advertisement.isTelevisionRoom(),
                advertisement.isRadioRoom(),
                advertisement.isBalconyRoom(),
                advertisement.isIronShared(),
                advertisement.isHooverShared(),
                advertisement.isTelevisionShared(),
                advertisement.isRadioShared(),
                advertisement.isBalconyShared(),
                advertisement.isActive()
        );
    }
    public AdvertisementShort mapAdvertisementToAdvertisementShortWithDate(Advertisement advertisement, List<Binary> photo, UserResponse userResponse, LocalDate dateFrom, LocalDate dateTo) {
        return new AdvertisementShort(
                advertisement.getId(),
                advertisement.getTitle(),
                advertisement.getCity(),
                advertisement.getDistrict().getName(),
                advertisement.getNumBeds(),
                advertisement.getRoomDescription(),
                advertisement.getRoomGender(),
                advertisement.getReservations()
                        .stream()
                        .filter(reservation -> compareDateOfReservationToDateOfSearch(reservation, dateFrom, dateTo))
                        .map(reservation -> mapGuestToGuestResponse(reservation.getGuest()))
                        .toList(),
                advertisement.getPrice(),
                advertisement.getStreetName(),
                advertisement.getRoomArea(),
                userResponse,
                photo,
                advertisement.isIronRoom(),
                advertisement.isHooverRoom(),
                advertisement.isTelevisionRoom(),
                advertisement.isRadioRoom(),
                advertisement.isBalconyRoom(),
                advertisement.isIronShared(),
                advertisement.isHooverShared(),
                advertisement.isTelevisionShared(),
                advertisement.isRadioShared(),
                advertisement.isBalconyShared(),
                advertisement.isActive()
        );
    }



    public Advertisement mapAdvertisementRequestToAdvertisement(AdvertisementRequest advertisementRequest, District district) {
        return new Advertisement(
                advertisementRequest.getHostId(),
                advertisementRequest.getTitle(),
                advertisementRequest.getCity(),
                district,
                advertisementRequest.getGenderRoom(),
                advertisementRequest.getPostCode(),
                advertisementRequest.getStreetName(),
                advertisementRequest.getRoomDescription(),
                advertisementRequest.getRoomArea(),
                advertisementRequest.getNumBeds(),
                advertisementRequest.getPrice(),
                advertisementRequest.getFirstStageDiscount(),
                advertisementRequest.getSecondStageDiscount(),
                advertisementRequest.getThirdStageDiscount(),
                advertisementRequest.getFourthStageDiscount(),
                advertisementRequest.getSharedBeds(),
                advertisementRequest.getLanguage(),
                advertisementRequest.isIronRoom(),
                advertisementRequest.isHooverRoom(),
                advertisementRequest.isTelevisionRoom(),
                advertisementRequest.isRadioRoom(),
                advertisementRequest.isBalconyRoom(),
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
                                                        AdvertisementRequest request, District district) {
        advertisement.setTitle(request.getTitle());
        advertisement.setDistrict(district);
        advertisement.setRoomGender(request.getGenderRoom());
        advertisement.setPostCode(request.getPostCode());
        advertisement.setStreetName(request.getStreetName());
        advertisement.setRoomDescription(request.getRoomDescription());
        advertisement.setRoomArea(request.getRoomArea());
        advertisement.setNumBeds(request.getNumBeds());
        advertisement.setPrice(request.getPrice());
        advertisement.setFirstStageDiscount(request.getFirstStageDiscount());
        advertisement.setSecondStageDiscount(request.getSecondStageDiscount());
        advertisement.setThirdStageDiscount(request.getThirdStageDiscount());
        advertisement.setFourthStageDiscount(request.getFourthStageDiscount());
        advertisement.setSharedBeds(request.getSharedBeds());
        advertisement.setLanguage(request.getLanguage());
        advertisement.setIronRoom(request.isIronRoom());
        advertisement.setHooverRoom(request.isHooverRoom());
        advertisement.setTelevisionRoom(request.isTelevisionRoom());
        advertisement.setRadioRoom(request.isRadioRoom());
        advertisement.setBalconyRoom(request.isBalconyRoom());
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

    private boolean compareDateOfReservationToDateOfSearch(Reservation reservation, LocalDate dateForm,LocalDate dateTo){
        return reservation.getDateTo().equals(dateForm)
                || (dateForm.isAfter(reservation.getDateFrom()) && dateForm.isBefore(reservation.getDateTo()))
                || (dateTo.isAfter(reservation.getDateFrom()) && dateTo.isBefore(reservation.getDateTo()))
                || (dateForm.isBefore(reservation.getDateFrom()) && dateTo.isAfter(reservation.getDateTo()));
    }

    private static String addRentalRules(List<String> rentalRules){
        if(!rentalRules.isEmpty())
        return String.join(",", rentalRules);

        return "";
    }

    private Integer calculatePrice(Advertisement advertisement){
        return null;
    }
}
