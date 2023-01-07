package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.request.AdvertisementMultisearch;
import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
import com.startup.bedok.advertisment.model.request.AdvertisementShort;
import com.startup.bedok.advertisment.model.response.AdvertisementDTO;
import com.startup.bedok.advertisment.services.AdvertisementService;
import com.startup.bedok.config.SpringFoxConfig;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("advertisement")
@RequiredArgsConstructor
@Api(tags = {SpringFoxConfig.BOOK_TAG})
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    private ResponseEntity<UUID> createAdvertisement(@RequestBody AdvertisementRequest advertisementRequest) {
        return ResponseEntity.ok(advertisementService.createAdvertisement(advertisementRequest));
    }

    @PutMapping
    private ResponseEntity<Advertisement> updateAdvertisement(@RequestBody AdvertisementRequest advertisementRequest, @RequestParam UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.updateAdvertisement(advertisementRequest, advertisementId));
    }

    @GetMapping
    private ResponseEntity<AdvertisementDTO> getAdvertisementById(@RequestParam UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementById(advertisementId));
    }

    @GetMapping("host")
    private ResponseEntity<List<AdvertisementShort>> getAdvertisementListByHostId(@RequestParam UUID hostId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementListByHostId(hostId));
    }

    @GetMapping("criteria")
    private ResponseEntity<Page<AdvertisementShort>> getAdvertisementListByMultiSearch(
            @RequestBody AdvertisementMultisearch advertisementMultisearch) {
        return ResponseEntity.ok(advertisementService.findAllWithFilters(advertisementMultisearch));
    }

    @GetMapping("details")
    private ResponseEntity<AdvertisementDTO> getAdvertisementByDetails(@RequestParam UUID advertisementId) {
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

    @PutMapping("all/photos")
    private ResponseEntity<String> addPhotosToAllAdvertisement(List<MultipartFile> photos) {
        return ResponseEntity.ok(advertisementService.saveRoomPhotostoAll(photos));
    }

    @PostMapping("random")
    private ResponseEntity<String> createSomeRandomAdvertisements(){
        advertisementService.createSomeRandomAdvertisements();
        return ResponseEntity.ok("ok");
    }

    @PostMapping("randomPhotos")
    private ResponseEntity<String> createSomePhotosForRandomAdvertisements() throws IOException {

        return ResponseEntity.ok( advertisementService.createSomePhotosForRandomAdvertisements());
    }
}
