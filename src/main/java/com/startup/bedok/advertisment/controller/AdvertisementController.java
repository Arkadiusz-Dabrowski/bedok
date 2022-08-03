package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.AdvertisementDTO;
import com.startup.bedok.advertisment.services.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    private ResponseEntity<Long> createAdvertisement(@Validated AdvertisementDTO advertisementDTO) {
        return ResponseEntity.ok(advertisementService.createAdvertisement(advertisementDTO));
    }

    @GetMapping
    private ResponseEntity<AdvertisementDTO> getByAdvertisementId(@PathVariable Long advertisementId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementById(advertisementId));
    }
}
