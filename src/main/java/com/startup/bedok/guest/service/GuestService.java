package com.startup.bedok.guest.service;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.services.AdvertisementService;
import com.startup.bedok.guest.model.GuestMapper;
import com.startup.bedok.guest.model.entity.Guest;
import com.startup.bedok.guest.model.request.GuestRequest;
import com.startup.bedok.guest.model.response.GuestResponse;
import com.startup.bedok.guest.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestService {

    @Autowired
    private final AdvertisementService advertisementService;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    GuestMapper guestMapper;

    @Transactional
    public UUID addGuestToAdvertisement(GuestRequest guestRequest, UUID advertisementId) {
        Advertisement advertisement = advertisementService.getAdvertisementById(advertisementId);
        Guest guest = new Guest(guestRequest.getName(), guestRequest.getAge(), guestRequest.getLanguage());
        guestRepository.save(guest);
        advertisement.getGuests().add(guest);
        return guest.getId();
    }

    public List<GuestResponse> deleteGuestFromAdvertisement(UUID guestId, UUID advertisementId) {
        Advertisement advertisement = advertisementService.getAdvertisementById(advertisementId);

        advertisement.setGuests(advertisement.getGuests().stream().filter(guest -> !guest.getId().equals(guestId)).collect(Collectors.toSet()));
        guestRepository.deleteById(guestId);
        return advertisement.getGuests().stream().map(guest -> guestMapper.mapGuestToGuestResponse(guest)).toList();
    }
}
