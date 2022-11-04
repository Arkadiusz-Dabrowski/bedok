//package com.startup.bedok.advertisment.repository;
//
//import com.startup.bedok.advertisment.model.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class AdvertisementRepositoryTest {
//
//    @Autowired
//    AdvertisementRepository advertisementRepository;
//
//    @Autowired
//    PriceRepository priceRepository;
//
//    @Test
//    void listShouldntBeEmpty(){
//        //given
//        List<Price> price = priceRepository.saveAll(Arrays.asList(new Price( 1, 10, 100.0)));
//        List<Equipment> equipment = Arrays.asList(new Equipment("IRON", "żelazko"));
//        PaymentType paymentType = new PaymentType("CACHE", "gotówka");
//        RoomPhoto roomPhoto = new RoomPhoto("testID");
//        Advertisement advertisement = new Advertisement(UUID.randomUUID(), "00-000", "ulica testowa",
//                Arrays.asList(roomPhoto), "testowy opis", 25.0, 5, 2, price, false, "Polski", equipment, equipment, Arrays.asList(paymentType), "" );
//
//        //when
//        advertisementRepository.save(advertisement);
//        //then
//       assertNotNull(advertisementRepository.findAll());
//    }
//
//}