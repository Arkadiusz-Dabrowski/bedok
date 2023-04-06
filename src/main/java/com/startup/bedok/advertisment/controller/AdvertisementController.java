package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.request.AdvertisementMultisearch;
import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
import com.startup.bedok.advertisment.model.response.AdvertisementShort;
import com.startup.bedok.advertisment.model.response.AdvertisementResponse;
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
    private ResponseEntity<AdvertisementResponse> getAdvertisementById(@RequestParam UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementDTOById(advertisementId));
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
    private ResponseEntity<AdvertisementResponse> getAdvertisementByDetails(@RequestParam UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementDTOById(advertisementId));
    }

    @GetMapping("list")
    private ResponseEntity<List<AdvertisementShort>> getAdvertisementList() {
        return ResponseEntity.ok(advertisementService.getAdvertisementsList());
    }

    @PutMapping("photos")
    private ResponseEntity<String> addPhotosToAdvertisement(List<MultipartFile> photos, @RequestParam UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.saveRoomPhotos(photos, advertisementId));
    }

    @PostMapping("random")
    private ResponseEntity<String> createSomeRandomAdvertisements(){
        advertisementService.createSomeRandomAdvertisements();
        return ResponseEntity.ok("ok");
    }

    @PostMapping("randomPhotos")
    private ResponseEntity<String> createSomePhotosForRandomAdvertisements() throws IOException {
        return ResponseEntity.ok(advertisementService.createSomePhotosForRandomAdvertisements());
    }

    @DeleteMapping("delete")
    private ResponseEntity<String> deleteAdvertisementById(UUID id) {
       return ResponseEntity.ok(advertisementService.deleteAdvertisementById(id).toString());
    }

    @PutMapping("deactivate")
    private ResponseEntity<String> deactivateAdvertisementById(UUID id) {
        return ResponseEntity.ok(advertisementService.deactivateAdvertisementById(id).toString());
    }
}
