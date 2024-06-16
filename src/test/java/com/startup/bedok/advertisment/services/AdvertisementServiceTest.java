package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceTest {

    @Mock
    AdvertisementRepository advertisementRepository;
    @InjectMocks
    AdvertisementService advertisementService;

    public void prepareData(){
        Advertisement advertisement = new Advertisement();
        advertisement.setHostId(UUID.randomUUID());
        advertisement.setCity("Warsaw");
        advertisement.setTitle("Comfortable Room in City Center");
        advertisement.setDistrict("Śródmieście");
        advertisement.setRoomGender(RoomGender.MALE);
        advertisement.setPostCode("00-001");
        advertisement.setStreetName("Marszałkowska");
        advertisement.setRoomDescription("A spacious room with a beautiful view.");
        advertisement.setRoomArea(20.5);
        advertisement.setNumBeds(1);
        advertisement.setPrice(1200.00);
        advertisement.setFirstStageDiscount(5.0);
        advertisement.setSecondStageDiscount(10.0);
        advertisement.setThirdStageDiscount(15.0);
        advertisement.setFourthStageDiscount(20.0);
        advertisement.setSharedBeds(true);
        advertisement.setLanguage("Polish");
        advertisement.setIronRoom(true);
        advertisement.setHooverRoom(true);
        advertisement.setTelevisionRoom(true);
        advertisement.setRadioRoom(true);
        advertisement.setBalconyRoom(true);
        advertisement.setIronShared(false);
        advertisement.setHooverShared(false);
        advertisement.setTelevisionShared(false);
        advertisement.setRadioShared(false);
        advertisement.setBalconyShared(false);
        advertisement.setCache(true);
        advertisement.setTransfer(false);
        advertisement.setRentalRulesObject("No pets allowed. No smoking.");
        advertisement.setUploadDate(Instant.now().toEpochMilli());
        advertisement.setUpdateDate(Instant.now().toEpochMilli());
        advertisement.setActive(true);
        advertisementRepository.save(advertisement);
}
    @Test
    void getAdvertisementById() {
        //given
        UUID id = UUID.randomUUID();
        when(advertisementRepository.findById(id)).thenReturn(Optional.of(new Advertisement()));
        //when
        advertisementService.getAdvertisementById(id);
        //then
        verify(advertisementRepository, atLeast(1)).findById(id);

    }
}