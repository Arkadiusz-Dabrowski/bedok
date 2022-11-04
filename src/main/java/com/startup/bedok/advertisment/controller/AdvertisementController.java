package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.AdvertisementDTO;
import com.startup.bedok.advertisment.model.AdvertisementRequest;
import com.startup.bedok.advertisment.model.AdvertisementShort;
import com.startup.bedok.advertisment.services.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    private ResponseEntity<UUID> createAdvertisement(@RequestBody AdvertisementRequest advertisementRequest) {
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

    @PutMapping("photos")
    private ResponseEntity<String> addPhotosToAdvertisement(List<MultipartFile> photos, @RequestParam UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.saveRoomPhotos(photos, advertisementId));
    }
}
