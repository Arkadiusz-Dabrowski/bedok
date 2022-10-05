package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.AdvertisementDTO;
import com.startup.bedok.advertisment.model.AdvertisementRequest;
import com.startup.bedok.advertisment.model.AdvertisementShort;
import com.startup.bedok.advertisment.services.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    private ResponseEntity<UUID> createAdvertisement(AdvertisementRequest advertisementRequest) {
        return ResponseEntity.ok(advertisementService.createAdvertisement(advertisementRequest));
    }

    @GetMapping
    private ResponseEntity<AdvertisementDTO> getAdvertisementById(@RequestParam UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementById(advertisementId));
    }

    @GetMapping("list")
    private ResponseEntity<List<AdvertisementShort>> getAdvertisementList() {
        return ResponseEntity.ok(advertisementService.getAdvertisementsList());
    }
}
