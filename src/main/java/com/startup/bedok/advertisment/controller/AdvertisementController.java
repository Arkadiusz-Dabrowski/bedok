package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.Advertisement;
import com.startup.bedok.advertisment.model.AdvertisementDTO;
import com.startup.bedok.advertisment.services.AdvertisementService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertisementController {

    private AdvertisementService advertisementService;

    @PostMapping
    private void createAdvertisement(AdvertisementDTO advertisementDTO){
        advertisementService.createAdvertisement(advertisementDTO);

    }
}
