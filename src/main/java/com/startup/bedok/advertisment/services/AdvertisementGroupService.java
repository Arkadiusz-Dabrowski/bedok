package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.AdvertisementGroup;
import com.startup.bedok.advertisment.model.request.AdvertisementGroupModifyRequest;
import com.startup.bedok.advertisment.repository.AdvertisementGroupRepository;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.exceptions.AdvertisementGroupNotExistsException;
import com.startup.bedok.exceptions.AdvertisementNotExistsException;
import com.startup.bedok.global.SimpleResponse;
import com.startup.bedok.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdvertisementGroupService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementGroupRepository advertisementGroupRepository;
    private final ReservationRepository reservationRepository;
    private final JwtTokenUtil jwtTokenUtil;


    @Transactional
    public AdvertisementGroup addAdvertisementToGroup(UUID groupId, UUID advertisementID, String token) {
        AdvertisementGroup advertisementGroup = advertisementGroupRepository.findById(groupId)
                .orElseThrow(() -> new AdvertisementGroupNotExistsException(groupId));
        Advertisement advertisement = advertisementRepository.findById(advertisementID)
                .orElseThrow(() -> new AdvertisementNotExistsException(advertisementID));
        isAdvertisementBelongToUser(token, advertisement);
        advertisement.setAdvertisementGroup(advertisementGroup);
        addAdvertisementReservationsToAdvertisementGroup(advertisement, advertisementGroup);
        advertisementGroup.addAdvertisementToGroup(advertisement);
        return advertisementGroup;
    }

    private void addAdvertisementReservationsToAdvertisementGroup(Advertisement advertisement, AdvertisementGroup advertisementGroup) {
        reservationRepository.findAllByAdvertisementId(advertisement.getId())
                .forEach(reservation -> reservation.setAdvertisementGroup(advertisementGroup));
    }


    @Transactional
    public AdvertisementGroup detachAdvertisementFromGroup(UUID groupId, UUID advertisementID, String token) {
        AdvertisementGroup advertisementGroup = advertisementGroupRepository.findById(groupId)
                .orElseThrow(() -> new AdvertisementGroupNotExistsException(groupId));
        Advertisement advertisement = advertisementRepository.findById(advertisementID)
                .orElseThrow(() -> new AdvertisementNotExistsException(advertisementID));
        isAdvertisementBelongToUser(token, advertisement);
        advertisement.setAdvertisementGroup(advertisementGroup);
        deleteAdvertisementReservationsFromAdvertisementGroup(advertisement, advertisementGroup);
        advertisementGroup.deleteAdvertisementFromGroup(advertisement);
        return advertisementGroup;
    }

    private void deleteAdvertisementReservationsFromAdvertisementGroup(Advertisement advertisement, AdvertisementGroup advertisementGroup) {
        reservationRepository.findAllByAdvertisementId(advertisement.getId())
                .forEach(reservation -> {
                    if (reservation.getAdvertisementGroup().equals(advertisementGroup))
                        reservation.setAdvertisementGroup(null);
                });
    }

    private void isAdvertisementBelongToUser(String token, Advertisement advertisement) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        if (!advertisement.getHostId().equals(userId)) {
            throw new IllegalArgumentException("Advertisement does not belong dateTo user");
        }
    }

}
