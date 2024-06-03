package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.request.AdvertisementMultisearch;
import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
import com.startup.bedok.advertisment.model.request.AdvertisementUpdateRequest;
import com.startup.bedok.advertisment.model.response.AdvertisementChangeStatusResponse;
import com.startup.bedok.advertisment.model.response.AdvertisementShort;
import com.startup.bedok.advertisment.model.response.AdvertisementResponse;
import com.startup.bedok.advertisment.services.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("advertisement")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    private ResponseEntity<UUID> createAdvertisement(@Valid @RequestBody AdvertisementRequest advertisementRequest,
                                                     @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.createAdvertisement(advertisementRequest, token));
    }

    @PutMapping("{advertisementId}")
    private ResponseEntity<AdvertisementResponse> updateAdvertisement(@Valid @RequestBody AdvertisementUpdateRequest advertisementRequest,
                                                              @PathVariable UUID advertisementId,
                                                              @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.updateAdvertisement(advertisementRequest, advertisementId, token));
    }

    @GetMapping
    private ResponseEntity<AdvertisementResponse> getAdvertisementById(@RequestParam UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementDTOById(advertisementId));
    }

    @GetMapping("host")
    private ResponseEntity<List<AdvertisementShort>> getAdvertisementListByHostId(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.getAdvertisementListByHostId(token));
    }

    @PostMapping("criteria")
    private ResponseEntity<Page<AdvertisementShort>> getAdvertisementListByMultiSearch(
            @RequestBody @Valid AdvertisementMultisearch advertisementMultisearch) {
        return ResponseEntity.ok(advertisementService.findAllWithFilters(advertisementMultisearch));
    }

    @GetMapping("details/{advertisementId}")
    private ResponseEntity<AdvertisementResponse> getAdvertisementByDetails(@PathVariable UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementDTOById(advertisementId));
    }

    @GetMapping("list")
    private ResponseEntity<List<AdvertisementShort>> getAdvertisementList() {
        return ResponseEntity.ok(advertisementService.getAdvertisementsList());
    }

    @PutMapping("photos/{advertisementId}")
    private ResponseEntity<String> addPhotosToAdvertisement(List<MultipartFile> photos, @PathVariable UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.saveRoomPhotos(photos, advertisementId));
    }

    @PostMapping("random")
    private ResponseEntity<List<Advertisement>> createSomeRandomAdvertisements(){
        return ResponseEntity.ok(advertisementService.createSomeRandomAdvertisements());
    }

    @PostMapping("randomPhotos")
    private ResponseEntity<String> createSomePhotosForRandomAdvertisements() throws IOException {
        return ResponseEntity.ok(advertisementService.createSomePhotosForRandomAdvertisements());
    }

    @DeleteMapping("delete/{advertisementId}")
    private ResponseEntity<AdvertisementChangeStatusResponse> deleteAdvertisementById(@PathVariable UUID advertisementId,
                                                           @RequestHeader("Authorization") String token) {
       return ResponseEntity.ok(advertisementService.deleteAdvertisementById(advertisementId, token));
    }

    @PatchMapping("deactivate/{advertisementId}")
    private ResponseEntity<AdvertisementChangeStatusResponse> deactivateAdvertisementById(@PathVariable UUID advertisementId,
                                                               @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.deactivateAdvertisementById(advertisementId, token));
    }

    @PatchMapping("activate/{advertisementId}")
    private ResponseEntity<AdvertisementChangeStatusResponse> activateAdvertisementById(@PathVariable UUID advertisementId,
                                                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.activateAdvertisementById(advertisementId, token));
    }

    @GetMapping("district")
    private ResponseEntity<Map<String, List<String>>> getDistrictsCollection(){
        return ResponseEntity.ok(advertisementService.getDistrictsCollection());
    }
}
